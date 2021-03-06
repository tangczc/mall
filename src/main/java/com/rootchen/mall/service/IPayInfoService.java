package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LC
 * @since 2019-06-25
 */
public interface IPayInfoService {

    /**
     * 支付宝支付
     *
     * @param session     session
     * @param request     request
     * @param orderNumber 订单号
     * @return
     */
    SR aliPay(HttpSession session, HttpServletRequest request, Long orderNumber);

    /**
     * 查询支付状态，前端轮询调用这个接口直到返回success
     *
     * @param session
     * @param orderNumber
     * @return
     */
    SR checkPayStatus(HttpSession session, Long orderNumber);
}
