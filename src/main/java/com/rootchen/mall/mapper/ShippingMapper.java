package com.rootchen.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rootchen.mall.entity.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-06-18
 */
public interface ShippingMapper extends BaseMapper<Shipping> {

    /**
     * 查询用户收获地址列表
     *
     * @param userId 用户id
     * @return
     */
    List<Shipping> selectShippingList(@Param("userId") Long userId);

    /**
     * 校验收获地址是否是已经登陆用户防止越权
     *
     * @param shippingId
     * @param userId
     * @return
     */
    Shipping checkShippingByUserId(@Param("shippingId") Long shippingId, @Param("userId") Long userId);

    /**
     * 查询收获地址详情
     *
     * @param shippingId
     * @return
     */
    Shipping selectShippingInfo(@Param("shippingId") Long shippingId);
}
