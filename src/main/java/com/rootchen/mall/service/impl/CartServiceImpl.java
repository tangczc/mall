package com.rootchen.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.Cart;
import com.rootchen.mall.entity.Product;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.mapper.CartMapper;
import com.rootchen.mall.mapper.ProductMapper;
import com.rootchen.mall.service.ICartService;
import com.rootchen.mall.util.BigDecimalUtil;
import com.rootchen.mall.util.PropertiesUtil;
import com.rootchen.mall.vo.CartProductVo;
import com.rootchen.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-06-17
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加商品到购物车
     *
     * @param session   session
     * @param count     商品数量
     * @param productId 商品id
     * @return
     */
    @Override
    public SR<CartVo> addCart(HttpSession session, Integer count, Long productId) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Cart cart = cartMapper.selectByUserIdAndProduct(user.getId(), productId);
        if (cart != null) {
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateById(cart);
            return this.list(user.getId());
        }
        Cart cartITem = Cart.builder()
                .quantity(count)
                .userId(user.getId())
                .checked(Const.Cart.CHECKED)
                .productId(productId)
                .build();
        cartMapper.insert(cartITem);
        return this.list(user.getId());
    }


    /**
     * 商品查询
     *
     * @param userId
     * @return
     */
    @Override
    public SR<CartVo> list(Long userId) {
        return SR.ok(this.getCartVo(userId));
    }

    /**
     * 更新购物车
     *
     * @param session   session
     * @param count     数量
     * @param productId 产品id
     * @return
     */
    @Override
    public SR<CartVo> updateCart(HttpSession session, Integer count, Long productId) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Cart cart = cartMapper.selectByUserIdAndProduct(user.getId(), productId);
        if (cart != null) {
            cart.setQuantity(count);
        }
        cartMapper.updateById(cart);
        return this.list(user.getId());
    }

    /**
     * 删除购物车商品
     *
     * @param session    session
     * @param productIds 产品id (String 用，分割)
     * @return
     */
    @Override
    public SR<CartVo> deleteProduct(HttpSession session, String productIds) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if (CollectionUtils.isEmpty(productList)) {
            return SR.error(SRCode.ILLEGAL_ARGUMENT.getCode(), SRCode.ILLEGAL_ARGUMENT.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        cartMapper.deleteByUserIdProductIds(user.getId(), productList);
        return this.list(user.getId());
    }

    /**
     * 全选 全反选
     * @param session   session
     * @param productId 用户id
     * @param checked   选中状态
     * @return
     */
    @Override
    public SR<CartVo> selectOrUnselectAll(HttpSession session, Long productId, Integer checked) {
        if (!CheckUser.isLoginSuccess(session)){
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        cartMapper.checkOrUncheckedProduct(user.getId(), productId, checked);
        return this.list(user.getId());
    }

    public SR getCartProductCount(HttpSession session){
        if (!CheckUser.isLoginSuccess(session)){
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        return SR.ok(cartMapper.selectCartProductCount(user.getId()));

    }
    /**
     * 获取购物车内容用于前端展示
     *
     * @param userId 用户id
     * @return
     */
    private CartVo getCartVo(Long userId) {
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setUserId(cartItem.getUserId());
                cartProductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectProductId(cartItem.getProductId());
                if (product != null) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FILE);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateById(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVo.setProductTotalPrice(new BigDecimal((BigDecimalUtil.multiply(product.getPrice().doubleValue(), cartProductVo.getQuantity())).toString()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    //如果勾选，增加到总价当中
                    cartTotalPrice = new BigDecimal((BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue())).toString());
                }
                cartProductVoList.add(cartProductVo);
            }
        }

        CartVo cartVo = CartVo.builder()
                .cartTotalPrice(cartTotalPrice)
                .cartProductVoList(cartProductVoList)
                .allChecked(this.getAllCheckedStatus(userId))
                .imageHost(PropertiesUtil.getProperty("ftp://192.168.1.106/"))
                .build();
        return cartVo;
    }

    /**
     * 校验用户购物车是否有选取
     *
     * @param userId
     * @return
     */
    private Boolean getAllCheckedStatus(Long userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
