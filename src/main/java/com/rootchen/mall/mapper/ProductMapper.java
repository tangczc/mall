package com.rootchen.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rootchen.mall.entity.Product;
import com.rootchen.mall.vo.ProductListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author LC
 * @since 2019-05-16
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 分页查询产品
     *
     * @return
     */
    List<ProductListVo> getProductList(Page page);

    /**
     * 查询产品详情
     *
     * @param id 产品id
     * @return
     */
    Product selectProductId(@Param("id") Long id);

}
