package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Shipping;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface IShippingService extends IService<Shipping> {

    /**
     * 添加收获地址
     *
     * @param session session
     * @param shippingParams 收货地址详情
     * @return
     */
    SR addShipping(HttpSession session, ShippingParams shippingParams);

}
