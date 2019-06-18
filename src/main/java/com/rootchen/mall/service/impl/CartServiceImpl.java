package com.rootchen.mall.service.impl;

import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.Cart;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.mapper.CartMapper;
import com.rootchen.mall.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;

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

    /**
     * 添加商品到购物车
     *
     * @param session   session
     * @param count     商品数量
     * @param productId 商品id
     * @return
     */
    @Override
    public SR addCart(HttpSession session, Integer count, Long productId) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Cart cart = cartMapper.selectByUserIdAndProduct(user.getId(),productId);
        if (cart == null){

        }
    }



}
