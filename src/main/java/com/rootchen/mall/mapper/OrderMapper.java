package com.rootchen.mall.mapper;

import com.rootchen.mall.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rootchen.mall.entity.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-06-27
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询该订单号是否是该用户的
     *
     * @param userId
     * @param orderNumber
     * @return
     */
    Order selectByUserIdAndOrderNumber(@Param("userId") Long userId, @Param("orderNumber") Long orderNumber);

}
