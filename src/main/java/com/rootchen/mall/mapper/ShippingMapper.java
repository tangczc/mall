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

    Shipping checkShippingByUserId(@Param("shippingId") Long shippingId,@Param("userId") Long userId);
}
