package com.rootchen.mall.common.pay;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.Utils;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.common.collect.Lists;
import com.rootchen.mall.common.SR;
import com.rootchen.mall.entity.OrderItem;
import com.rootchen.mall.util.BigDecimalUtil;
import com.rootchen.mall.util.FTPUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 支付 支付宝&微信
 * @author: LiChen
 * @create: 2019-06-27 20:42
 */
@Log4j2
public class AliPay {

    /**
     * 支付宝当面付2.0服务
     */
    private static AlipayTradeService tradeService;

    /**
     * 支付宝当面付2.0服务（集成了交易保障接口逻辑）
     */
    private static AlipayTradeService tradeWithHBService;

    /**
     * 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
     */
    private static AlipayMonitorService monitorService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("json").build();
    }

    /**
     * 测试当面付2.0生成支付二维码
     */
    public static SR<String> tradePrecreate(List<OrderItem> orderItemList, String payment, Long orderNumber, String path) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = orderNumber.toString();

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "晨哥的小商店";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = payment;

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("购买商品一共").append(orderItemList.size()).append("件").toString();

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "001";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "001";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        for (OrderItem orderItem : orderItemList) {
            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            GoodsDetail goodsDetail = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    BigDecimalUtil.multiply(orderItem.getCurrentUnitPrice().doubleValue(), new Double(100).doubleValue()).longValue(),
                    orderItem.getQuantity());
            // 创建好一个商品后添加至商品明细列表

            goodsDetailList.add(goodsDetail);

        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                File folder = new File(path);
                if (!folder.exists()) {
                    folder.setWritable(true);
                    folder.mkdirs();
                }

                // 需要修改为运行机器上的路径
                String qrPath = String.format(path + "/qr-%s.png", response.getOutTradeNo());
                String qrName = String.format("/qr-%s.png", response.getOutTradeNo());
                log.info("qrPath:" + qrPath);
                log.info("qrName:" + qrName);
                //todo 图片上传
                File targetFile = ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                    targetFile.delete();
                } catch (IOException e) {
                    log.info("文件上传异常");
                    e.printStackTrace();
                }
                return SR.ok(targetFile.getName());

            case FAILED:
                log.error("支付宝预下单失败!!!");
                return SR.errorMsg("支付宝预下单失败");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return SR.errorMsg("系统异常，预下单状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return SR.errorMsg("不支持的交易状态，交易返回异常!!!");
        }

    }

    /**
     * 测试当面付2.0查询订单
     */
    public static SR<String> tradeQuery(String outTradeNo) {
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
//        String outTradeNo = "tradepay14817938139942440181";

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");

                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);

                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                return SR.okMsg("订单支付成功");

            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                return SR.errorMsg("查询返回该订单支付失败或被关闭!!");


            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                return SR.errorMsg("系统异常，订单支付状态未知!!!");
            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return SR.errorMsg("不支持的交易状态，交易返回异常!!!");
        }
    }


    // 简单打印应答
    private static void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }
}
