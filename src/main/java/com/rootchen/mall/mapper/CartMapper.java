package com.rootchen.mall.mapper;

import com.rootchen.mall.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-06-17
 */
public interface CartMapper extends BaseMapper<Cart> {
    /**
     * 查询用户购物车是否有要添加到商品
     *
     * @param userId
     * @param productId
     * @return
     */
    Cart selectByUserIdAndProduct(@Param("userId") Long userId,@Param("productId") Long productId);
}
