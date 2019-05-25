package com.rootchen.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rootchen.mall.entity.Product;

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
     * 查询产品
     *
     * @return
     */
    Page<Product> getProductList(Page page);

}
