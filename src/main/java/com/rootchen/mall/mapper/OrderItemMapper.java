package com.rootchen.mall.mapper;

import com.rootchen.mall.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-06-27
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    /**
     * 查询订单里的商品
     *
     * @param orderNumber
     * @param userId
     * @return
     */
    List<OrderItem> selectOrderItemByOrderNoAndUserId(@Param("orderNumber") Long orderNumber,@Param("userId") Long userId);
}
