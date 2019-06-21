package com.rootchen.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ShippingServiceImpl extends ServiceImpl<ShippingMapper, Shipping> implements IShippingService {

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
        Shipping shipping = Shipping.builder()
                .userId(user.getId())
                .receiverAddress(shippingParams.getReceiverAddress())
                .receiverCity(shippingParams.getReceiverCity())
                .receiverDistrict(shippingParams.getReceiverDistrict())
                .receiverMobile(shippingParams.getReceiverMobile())
                .receiverName(shippingParams.getReceiverName())
                .receiverPhone(shippingParams.getReceiverPhone())
                .receiverProvince(shippingParams.getReceiverProvince())
                .receiverZip(shippingParams.getReceiverZip())
                .build();
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
        Page<Shipping> shippingPage = new Page<>(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectShippingList(user.getId());
        IPage<Shipping> shippingIPage = shippingPage.setRecords(shippingList);
        return SR.ok(shippingIPage);

    }
}
