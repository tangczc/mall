package com.rootchen.mall.service.impl;

import com.rootchen.mall.entity.OrderItem;
import com.rootchen.mall.mapper.OrderItemMapper;
import com.rootchen.mall.service.IOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-06-27
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
