package com.rootchen.mall.service.impl;

import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.common.pay.AliPay;
import com.rootchen.mall.entity.Order;
import com.rootchen.mall.entity.OrderItem;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.mapper.OrderItemMapper;
import com.rootchen.mall.mapper.OrderMapper;
import com.rootchen.mall.service.IPayInfoService;
import com.rootchen.mall.util.PropertiesUtil;
import com.rootchen.mall.vo.AliPayQrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @description: 支付实现类
 * @author: LiChen
 * @create: 2019-06-27 21:27
 */
@Service
public class PayInfoServiceImpl implements IPayInfoService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    /**
     * 支付宝支付
     *
     * @param session     session
     * @param request     request
     * @param orderNumber 订单号
     * @return
     */
    @Override
    public SR<AliPayQrVo> aliPay(HttpSession session, HttpServletRequest request, Long orderNumber) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }

        String path = request.getSession().getServletContext().getRealPath("upload");
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Order order = orderMapper.selectByUserIdAndOrderNumber(user.getId(), orderNumber);
        if (order == null) {
            return SR.errorMsg("订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.selectOrderItemByOrderNoAndUserId(orderNumber,user.getId());
        SR sr = AliPay.tradePrecreate(orderItemList,orderNumber, path);
        if (!sr.success()) {
            return sr;
        }
        String qrPath = PropertiesUtil.getProperty("ftp.server.http.prefix") + (String) sr.getData();
        AliPayQrVo aliPayQrVo = AliPayQrVo.builder()
                .orderNumber(String.valueOf(orderNumber))
                .qrPath(qrPath)
                .build();

        return SR.ok(aliPayQrVo);
    }
}
