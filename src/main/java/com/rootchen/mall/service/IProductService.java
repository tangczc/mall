package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LC
 * @since 2019-05-16
 */
public interface IProductService {

    /**
     * 添加商品
     *
     * @param session session
     * @param product 商品信息
     * @return
     */
    SR addOrUpdateProduct(HttpSession session,Product product);

    /**
     * 产品上下架
     *
     * @param session session
     * @param productId 产品id
     * @param status 产品状态
     * @return
     */
    SR setProductStatus(HttpSession session,Long productId,Integer status);
}
