package com.rootchen.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rootchen.mall.entity.Order;
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
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询该订单号是否是该用户的
     *
     * @param userId
     * @param orderNumber
     * @return
     */
    Order selectByUserIdAndOrderNumber(@Param("userId") Long userId, @Param("orderNumber") Long orderNumber);

    /**
     * 查询用户订单列表
     *
     * @param page   分页
     * @param userId 用户id
     * @return
     */
    List<Order> selectOrderListByUserId(Page page, @Param("userId") Long userId);


}
