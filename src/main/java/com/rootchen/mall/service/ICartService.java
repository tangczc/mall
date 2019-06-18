package com.rootchen.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Cart;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LC
 * @since 2019-06-17
 */
public interface ICartService extends IService<Cart> {

    /**
     * 添加商品到购物车
     *
     * @param session   session
     * @param count     商品数量
     * @param productId 商品id
     * @return
     */
    SR addCart(HttpSession session, Integer count, Long productId);

    /**
     * 查询商品
     *
     * @param userId 用户id
     * @return
     */
    SR list(Long userId);

    /**
     * 更新购物车商品
     *
     * @param session   session
     * @param count     数量
     * @param productId 产品id
     * @return
     */
    SR updateCart(HttpSession session, Integer count, Long productId);

    /**
     * 删除购物车商品
     *
     * @param session    session
     * @param productIds 产品id (String 用，分割)
     * @return
     */
    SR deleteProduct(HttpSession session, String productIds);

    /**
     * 全选 全反选
     *
     * @param session   session
     * @param productId 用户id
     * @param checked   选中状态
     * @return
     */
    SR selectOrUnselectAll(HttpSession session, Long productId, Integer checked);

    /**
     * 获取购物车商品总数
     *
     * @param session
     * @return
     */
    SR getCartProductCount(HttpSession session);
}
