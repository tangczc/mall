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
     * @param session    session
     * @param shippingId 收获地址id
     * @return
     */
    SR create(HttpSession session, Long shippingId);

    /**
     * 取消订单
     *
     * @param session     session
     * @param orderNumber 订单编号
     * @return
     */
    SR cancel(HttpSession session, Long orderNumber);

    /**
     * 购物车详情
     *
     * @param session     session
     * @param orderNumber 订单编号
     * @return
     */
    SR getOrderCartProduct(HttpSession session, Long orderNumber);


    /**
     * 查询订单列表页
     *
     * @param session  session
     * @param pageNum  当前页数
     * @param pageSize 总页数
     * @return
     */
    SR getOrderList(HttpSession session, Integer pageNum, Integer pageSize);

}
