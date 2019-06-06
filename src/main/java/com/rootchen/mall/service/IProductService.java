package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Product;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 服务类
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
    SR addOrUpdateProduct(HttpSession session, Product product);

    /**
     * 产品上下架
     *
     * @param session   session
     * @param productId 产品id
     * @param status    产品状态
     * @return
     */
    SR setProductStatus(HttpSession session, Long productId, Integer status);

    /**
     * 查找产品分页显示
     *
     * @param session  session
     * @param pageNum  页数
     * @param pageSize 总数
     * @return
     */
    SR getProductList(HttpSession session, Integer pageNum, Integer pageSize);

    /**
     * 查找产品详情
     *
     * @param session session
     * @param productId 产品id
     * @return
     */
    SR getProductDetail(HttpSession session,Long productId);

    /**
     * 商品查找
     *
     * @param session session
     * @param productId 商品 id
     * @param productName 商品名称
     * @param pageNum 页数
     * @param pageSize 总数
     * @return
     */
    SR productSearch(HttpSession session,Long productId,String productName,Integer pageNum, Integer pageSize);
}
