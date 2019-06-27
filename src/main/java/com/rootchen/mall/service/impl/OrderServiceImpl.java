package com.rootchen.mall.service.impl;

import com.rootchen.mall.entity.Order;
import com.rootchen.mall.mapper.OrderMapper;
import com.rootchen.mall.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
