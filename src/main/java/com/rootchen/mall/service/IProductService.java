package com.rootchen.mall.service;

import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
     * 后台添加商品
     *
     * @param session session
     * @param product 商品信息
     * @return
     */
    SR addOrUpdateProduct(HttpSession session, Product product);

    /**
     * 后台产品上下架
     *
     * @param session   session
     * @param productId 产品id
     * @param status    产品状态
     * @return
     */
    SR setProductStatus(HttpSession session, Long productId, Integer status);

    /**
     * 后台查找产品分页显示
     *
     * @param session  session
     * @param pageNum  页数
     * @param pageSize 总数
     * @return
     */
    SR getProductList(HttpSession session, Integer pageNum, Integer pageSize);

    /**
     * 后台查找产品详情
     *
     * @param session   session
     * @param productId 产品id
     * @return
     */
    SR getProductDetail(HttpSession session, Long productId);

    /**
     * 后台商品查找
     *
     * @param session     session
     * @param productId   商品 id
     * @param productName 商品名称
     * @param pageNum     页数
     * @param pageSize    总数
     * @return
     */
    SR productSearch(HttpSession session, Long productId, String productName, Integer pageNum, Integer pageSize);

    /**
     * 文件上传
     *
     * @param session       session
     * @param multipartFile 文件
     * @param request       request
     * @return
     */
    SR upload(HttpSession session, MultipartFile multipartFile, HttpServletRequest request);

    /**
     * 前台查找产品详情
     *
     * @param productId
     * @return
     */
    SR getPortalProductDetail(Long productId);

    /**
     * 前台台商品搜索
     *
     * @param productId   商品id
     * @param productName 商品名
     * @param pageNum     页数
     * @param pageSize    总数
     * @return
     */
    SR searchProduct(Long productId, String productName, Integer pageNum, Integer pageSize);

    /**
     * 列表 动态排序
     *
     * @param keyword    关键字
     * @param categoryId 产品id
     * @param pageNum    页数
     * @param pageSize   总页数
     * @param orderBy    排序方式
     * @return
     */
    SR getProductByKeywordCategory(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy);
}
