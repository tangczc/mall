package com.rootchen.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.rootchen.mall.common.CheckUser;
import com.rootchen.mall.common.Const;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.common.SRCode;
import com.rootchen.mall.entity.Category;
import com.rootchen.mall.entity.Product;
import com.rootchen.mall.mapper.CategoryMapper;
import com.rootchen.mall.mapper.ProductMapper;
import com.rootchen.mall.service.ICategoryService;
import com.rootchen.mall.service.IProductService;
import com.rootchen.mall.util.FTPUtil;
import com.rootchen.mall.util.PropertiesUtil;
import com.rootchen.mall.vo.FileUploadResultVo;
import com.rootchen.mall.vo.ProductDetailVo;
import com.rootchen.mall.vo.ProductListVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-05-16
 */
@Log4j2
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

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
    public SR<IPage> getProductList(HttpSession session, Integer pageNum, Integer pageSize) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        Page<ProductListVo> productPage = new Page<>(pageNum, pageSize);
        List<ProductListVo> productListVos = productMapper.getProductList(productPage);
        for (ProductListVo productListVo : productListVos) {
            productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        }
        IPage<ProductListVo> productIPage = productPage.setRecords(productListVos);
        return SR.ok(productIPage);
    }

    /**
     * 商品查找
     *
     * @param session     session
     * @param productId   商品 id
     * @param productName 商品名称
     * @param pageNum     页数
     * @param pageSize    总数
     * @return
     */
    @Override
    public SR<IPage> productSearch(HttpSession session, Long productId, String productName, Integer pageNum, Integer pageSize) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        IPage<ProductListVo> ProductListVoIpage = getProductListVoIPage(productId, productName, pageNum, pageSize);
        return SR.ok(ProductListVoIpage);

    }

    /**
     * 查找产品详情
     *
     * @param session   session
     * @param productId 产品id
     * @return
     */
    @Override
    public SR<ProductDetailVo> getProductDetail(HttpSession session, Long productId) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        Product product = productMapper.selectProductId(productId);
        if (product == null) {
            return SR.errorMsg("产品已经下架或者删除");
        }
        ProductDetailVo productDetailVo = getProductDetailVo(product);
        return SR.ok(productDetailVo);
    }

    /**
     * 文件上传
     *
     * @param session       session
     * @param multipartFile 文件
     * @param request       request
     * @return
     */
    @Override
    public SR<FileUploadResultVo> upload(HttpSession session, MultipartFile multipartFile, HttpServletRequest request) {
        SR sr = CheckUser.checkUser(session);
        if (!sr.success()) {
            return sr;
        }
        //获取本地图片上传路径
        String path = request.getSession().getServletContext().getRealPath("upload");
        //获取文件名称
        String fileName = multipartFile.getOriginalFilename();
        //获取文件扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //生成新的随机文件名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        log.info("开始上传文件，上传文件名:{},上传路径:{},新文件名字:{}", fileName, path, uploadFileName);
        //创建文件夹
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //上传文件之本地
        File targetFile = new File(path, uploadFileName);
        try {
            multipartFile.transferTo(targetFile);
            //todo 把本地文件上传至ftp服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //删除本地文件
            targetFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return SR.errorMsg("上传失败");
        }
        fileName = targetFile.getName();
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + fileName;
        FileUploadResultVo fileUploadResultVo = FileUploadResultVo.builder()
                .fileName(fileName)
                .url(url)
                .build();
        return SR.ok("上传成功", fileUploadResultVo);
    }

    /**
     * 前台查询产品详情
     *
     * @param productId
     * @return
     */
    public SR<ProductDetailVo> getPortalProductDetail(Long productId) {
        Product product = productMapper.selectProductId(productId);
        if (product == null) {
            return SR.errorMsg("产品已经下架或者删除");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return SR.errorMsg("产品已经下架或者删除");
        }
        ProductDetailVo productDetailVo = getProductDetailVo(product);
        return SR.ok(productDetailVo);
    }

    /**
     * 前台商品搜索
     *
     * @param productId   商品id
     * @param productName 商品名
     * @param pageNum     页数
     * @param pageSize    总数
     * @return
     */
    @Override
    public SR<IPage> searchProduct(Long productId, String productName, Integer pageNum, Integer pageSize) {
        IPage<ProductListVo> ProductListVoIpage = getProductListVoIPage(productId, productName, pageNum, pageSize);
        return SR.ok(ProductListVoIpage);
    }

    @Override
    public SR<IPage> getProductByKeywordCategory(String keyword, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return SR.error(SRCode.ILLEGAL_ARGUMENT.getCode(), SRCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Long> categoryIdList = new ArrayList<>();
        if (categoryId != null) {
            Category category = categoryMapper.selectById(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                //没有该分类，并且没有关键字，返回一个空，不报错
                Page<ProductListVo> productListVoPage = new Page<>(pageNum, pageSize);
                List<ProductListVo> productListVoList = Lists.newArrayList();
                IPage<ProductListVo> iPage = productListVoPage.setRecords(productListVoList);
                return SR.ok(iPage);
            }
            categoryIdList = iCategoryService.getDeepCategory(category.getId()).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        Page<ProductListVo> productListVoPage = new Page<>(pageNum, pageSize);
        List<Product> productList = Lists.newArrayList();
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                orderBy = new StringBuilder().append(orderByArray[0]).append(" ").append(orderByArray[1]).toString();
                productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, categoryIdList.size() == 0 ? null : categoryIdList, orderBy);
            }
        }
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList) {
            productListVoList.add(assembleProductiListVO(product));
        }

        return SR.ok("查询成功", productListVoPage.setRecords(productListVoList));
    }

    /**
     * 获取ProductVo用户前端展示
     *
     * @param product 产品实例
     * @return
     */
    private ProductDetailVo getProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = ProductDetailVo.builder()
                .id(product.getId())
                .subtitle(product.getSubtitle())
                .categoryId(product.getCategoryId())
                .detail(product.getDetail())
                .name(product.getName())
                .price(product.getPrice())
                .mainImage(product.getMainImage())
                .subImages(product.getSubImages())
                .status(product.getStatus())
                .stock(product.getStock())
                .createTime(product.getCreateTime())
                .updateTime(product.getUpdateTime())
                .build();

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "ftp://192.168.1.106"));
        Category category = categoryMapper.selectByCategoryId(product.getCategoryId());
        if (category == null) {
            productDetailVo.setParentCategoryId(0);
        } else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        return productDetailVo;
    }


    /**
     * 前后台商品查找商品封装
     *
     * @param productId
     * @param productName
     * @param pageNum
     * @param pageSize
     * @return
     */
    private IPage<ProductListVo> getProductListVoIPage(Long productId, String productName, Integer pageNum, Integer pageSize) {
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        Page<ProductListVo> productPage = new Page<>(pageNum, pageSize);

        List<ProductListVo> productListVos = productMapper.selectByproductIdAndproductName(productPage, productId, productName);
        for (ProductListVo productListVo : productListVos) {
            productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        }
        return productPage.setRecords(productListVos);
    }

    private ProductListVo assembleProductiListVO(Product product) {
        ProductListVo productListVo = ProductListVo.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .imageHost(product.getMainImage())
                .mainImage(product.getMainImage())
                .price(product.getPrice())
                .subtitle(product.getSubtitle())
                .status(product.getStatus())
                .build();
        return productListVo;
    }
}
