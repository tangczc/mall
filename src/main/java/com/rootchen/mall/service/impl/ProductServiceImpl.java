package com.rootchen.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.Product;
import com.rootchen.mall.mapper.ProductMapper;
import com.rootchen.mall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-05-16
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 添加商品
     *
     * @param session session
     * @param product 商品信息
     * @return
     */
    @Override
    public SR<String> addOrUpdateProduct(HttpSession session, Product product) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        if (product == null) {
            return SR.errorMsg("产品参数不能为空");
        }
        if (StringUtils.isNotBlank(product.getSubImages())) {
            String[] subImageArray = product.getSubImages().split(",");
            if (subImageArray.length > 0) {
                product.setMainImage(subImageArray[0]);
            }
        }
        int resultCount;
        if (product.getId() == null) {
            resultCount = productMapper.insert(product);
            if (resultCount > 0) {
                return SR.okMsg("商品添加成功");
            }
            return SR.errorMsg("商品添加失败");
        }
        resultCount = productMapper.updateById(product);
        if (resultCount > 0) {
            return SR.okMsg("更新商品成功");
        }
        return SR.errorMsg("更新商品失败");
    }

    /**
     * 产品上下架
     *
     * @param session   session
     * @param productId 产品id
     * @param status    产品状态
     * @return
     */
    @Override
    public SR<String> setProductStatus(HttpSession session, Long productId, Integer status) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        if (productId == null || status == null) {
            return SR.errorMsg("参数不能为空");
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int resultCount = productMapper.updateById(product);
        if (resultCount > 0) {
            return SR.okMsg("修改产品状态成功成功");
        }
        return SR.errorMsg("修改产品状态失败");
    }

    /**
     * 查找产品分页显示
     *
     * @param session  session
     * @param pageNum  页数
     * @param pageSize 总数
     * @return
     */
    @Override
    public SR<PageInfo<Product>> getProductList(HttpSession session, Integer pageNum, Integer pageSize) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.getProductList();
        PageInfo<Product> pageInfo = new PageInfo(productList);
        return SR.ok(pageInfo);
    }


}
