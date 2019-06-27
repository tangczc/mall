package com.rootchen.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.Shipping;
import com.rootchen.mall.entity.User;
import com.rootchen.mall.mapper.ShippingMapper;
import com.rootchen.mall.params.ShippingParams;
import com.rootchen.mall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-06-18
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 添加收获地址
     *
     * @param session        session
     * @param shippingParams 收货地址详情
     * @return
     */
    @Override
    public SR<String> addShipping(HttpSession session, ShippingParams shippingParams) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Shipping shipping = getShipping(shippingParams, user.getId());
        int count = shippingMapper.insert(shipping);
        if (count > 0) {
            return SR.okMsg("添加成功");
        }
        return SR.errorMsg("添加失败");
    }

    /**
     * 收获地址列表 （分页）
     *
     * @param session  session
     * @param pageNum  页数
     * @param pageSize 每页显示
     * @return
     */
    @Override
    public SR<IPage<Shipping>> showShipping(HttpSession session, int pageNum, int pageSize) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Page<Shipping> shippingPage = new Page<>(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectShippingList(user.getId());
        IPage<Shipping> shippingIPage = shippingPage.setRecords(shippingList);
        return SR.ok(shippingIPage);

    }

    /**
     * 更新收获地址
     *
     * @param session        session
     * @param shippingParams 更新收货地址参数
     * @return
     */
    @Override
    public SR updateShipping(HttpSession session, ShippingParams shippingParams) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        if (shippingParams.getShippingId() == null) {
            return SR.errorMsg("收获地址id不能为空");
        }
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        Shipping shipping = shippingMapper.checkShippingByUserId(shippingParams.getShippingId(), user.getId());
        if (shipping == null) {
            return SR.errorMsg("参数错误");
        }
        shipping = getShipping(shippingParams, user.getId());
        int count = shippingMapper.updateById(shipping);
        if (count > 0) {
            return SR.okMsg("更新成功");
        }
        return SR.errorMsg("更新失败");
    }


    /**
     * 收获地址详情
     *
     * @param session    session
     * @param shippingId 收获地址id
     * @return
     */
    @Override
    public SR showShippingInfo(HttpSession session, Long shippingId) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        Shipping shipping = shippingMapper.selectShippingInfo(shippingId);
        return SR.ok(shipping);
    }

    /**
     * 获取收获地址对象
     *
     * @param shippingParams
     * @param userId
     * @return
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Shipping getShipping(ShippingParams shippingParams, Long userId) {
        Shipping shipping = Shipping.builder()
                .receiverAddress(shippingParams.getReceiverAddress())
                .receiverCity(shippingParams.getReceiverCity())
                .receiverDistrict(shippingParams.getReceiverDistrict())
                .receiverMobile(shippingParams.getReceiverMobile())
                .receiverName(shippingParams.getReceiverName())
                .receiverPhone(shippingParams.getReceiverPhone())
                .receiverProvince(shippingParams.getReceiverProvince())
                .receiverZip(shippingParams.getReceiverZip())
                .build();
        if (shippingParams.getShippingId() != null) {
            shipping.setId(shippingParams.getShippingId());
        }
        if (userId != null) {
            return shipping.setUserId(userId);
        }
        return shipping;
    }
}
