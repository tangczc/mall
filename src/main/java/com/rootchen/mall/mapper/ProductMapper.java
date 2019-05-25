package com.rootchen.mall.mapper;

import com.rootchen.mall.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
     * 查询产品
     *
     * @return
     */
    List<Product> getProductList();

}
