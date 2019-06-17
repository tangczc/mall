package com.rootchen.mall.service.impl;

import com.rootchen.mall.entity.Cart;
import com.rootchen.mall.mapper.CartMapper;
import com.rootchen.mall.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-06-17
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

}
