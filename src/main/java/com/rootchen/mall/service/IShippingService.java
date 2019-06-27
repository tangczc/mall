package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.params.ShippingParams;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LC
 * @since 2019-06-18
 */
public interface IShippingService {

    /**
     * 添加收获地址
     *
     * @param session        session
     * @param shippingParams 收货地址详情
     * @return
     */
    SR addShipping(HttpSession session, ShippingParams shippingParams);

    /**
     * 收获地址列表 （分页）
     *
     * @param session  session
     * @param pageNum  页数
     * @param pageSize 每页显示
     * @return
     */
    SR showShipping(HttpSession session, int pageNum, int pageSize);

    /**
     * 更新收货地址
     *
     * @param session        session
     * @param shippingParams 更新收货地址参数
     * @return
     */
    SR updateShipping(HttpSession session, ShippingParams shippingParams);

    /**
     * 收获地址详情
     *
     * @param session    session
     * @param shippingId 收获地址id
     * @return
     */
    SR showShippingInfo(HttpSession session, Long shippingId);

}
