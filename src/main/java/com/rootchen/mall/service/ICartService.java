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
     * @param session session
     * @param count 商品数量
     * @param productId 商品id
     * @return
     */
    SR addCart(HttpSession session, Integer count, Long productId);

}
