package com.rootchen.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rootchen.mall.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    Cart selectByUserIdAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 查询用户的购物车未选商品数量
     *
     * @param userId
     * @return
     */
    int selectCartProductCheckedStatusByUserId(@Param("userId") Long userId);

    /**
     * 查询用户购物车列表
     *
     * @param userId
     * @return
     */
    List<Cart> selectCartByUserId(@Param("userId") Long userId);

    /**
     * 删除购物车商品
     *
     * @param userId
     * @param productIdList
     * @return
     */
    int deleteByUserIdProductIds(@Param("userId") Long userId, @Param("productIdList") List<String> productIdList);

    /**
     * 全选 反全选
     *
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    int checkOrUncheckedProduct(@Param("userId") Long userId, @Param("productId") Long productId, @Param("checked") Integer checked);

    /**
     * 获取购物车商品总数
     *
     * @param userId
     * @return
     */
    int selectCartProductCount(@Param("userId") Long userId);


}
