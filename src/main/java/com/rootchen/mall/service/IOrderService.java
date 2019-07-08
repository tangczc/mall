package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LC
 * @since 2019-06-27
 */
public interface IOrderService {

    /**
     * 创建订单
     *
     * @param session session
     * @param shippingId 收获地址id
     * @return
     */
    SR create(HttpSession session, Long shippingId);

}
