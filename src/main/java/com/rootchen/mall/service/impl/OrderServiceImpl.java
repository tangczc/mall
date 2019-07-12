package com.rootchen.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.rootchen.mall.common.*;
import com.rootchen.mall.entity.*;
import com.rootchen.mall.mapper.*;
import com.rootchen.mall.service.IOrderService;
import com.rootchen.mall.util.BigDecimalUtil;
import com.rootchen.mall.util.PropertiesUtil;
import com.rootchen.mall.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LC
 * @since 2019-06-27
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 创建订单
     *
     * @param session    session
     * @param shippingId 收获地址id
     * @return
     */
    @Override
    public SR create(HttpSession session, Long shippingId) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        Long userId = ((User) session.getAttribute(Const.CURRENT_USER)).getId();

        //获取购物车里选中的商品
        List<Cart> cartList = cartMapper.selectCheckedByUserId(userId);
        SR sr = this.getCartOrderItem(userId, cartList);
        if (!sr.success()) {
            return sr;
        }
        //计算商品总价
        List<OrderItem> orderItemList = (List<OrderItem>) sr.getData();
        BigDecimal payment = this.getPayment(orderItemList);

        //生成订单
        Order order = this.getOrder(userId, shippingId, payment);
        if (order == null) {
            return SR.errorMsg("生成订单错误");
        }
        if (CollectionUtils.isEmpty(orderItemList)) {
            return SR.errorMsg("购物车为空");
        }
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderNo(order.getOrderNo());
        }
        //批量插入
        orderItemMapper.batchInsert(orderItemList);
        //减少产品数量
        this.reduceProductStock(orderItemList);
        //清空购物车
        this.cleanCart(cartList);
        //返回订单明细
        OrderVo orderVo = getOrderVo(order, orderItemList);
        return SR.ok(orderVo);

    }


    /**
     * 获取订单明细
     *
     * @param userId   用户id
     * @param cartList 购物车列表
     * @return
     */
    private SR<List<OrderItem>> getCartOrderItem(Long userId, List<Cart> cartList) {
        List<OrderItem> orderItemList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(cartList)) {
            return SR.errorMsg("购物车为空");
        }
        //校验购物车数据，产品状态和数量
        for (Cart cartItem : cartList) {
            OrderItem orderItem = OrderItem.builder().build();
            Product product = productMapper.selectProductId(cartItem.getProductId());
            //校验产品状态
            if (Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()) {
                return SR.errorMsg("产品" + product.getName() + "不是在线售卖状态");
            }
            //校验产品数量
            if (cartItem.getQuantity() > product.getStock()) {
                return SR.errorMsg("产品" + product.getName() + "库存不足");
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(new BigDecimal(BigDecimalUtil.multiply(product.getPrice().doubleValue(), cartItem.getQuantity()).toString()));
            orderItemList.add(orderItem);
        }
        return SR.ok(orderItemList);
    }

    /**
     * 获取购物车总价
     *
     * @param orderItemList 购物车明细
     * @return
     */
    private BigDecimal getPayment(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = new BigDecimal(BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue()));
        }
        return payment;
    }


    /**
     * 生成订单
     *
     * @param userId     用户id
     * @param shippingId 收获地址
     * @param payment    总价
     * @return
     */
    private Order getOrder(Long userId, Long shippingId, BigDecimal payment) {
        Order order = Order.builder().build();

        long orderNumber = this.generateOrderNumber();
        order.setOrderNo(orderNumber);
        order.setStatus(Const.OrderStatusEnum.NO_PAY.getCode());
        order.setPostage(0);
        order.setPaymentType(1);
        order.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setPayment(payment);
        order.setUserId(userId);
        order.setShippingId(shippingId);

        int count = orderMapper.insert(order);
        if (count < 0) {
            return null;
        }
        return order;
    }

    /**
     * 生成订单号
     *
     * @return
     */
    private long generateOrderNumber() {
        //生成规则  时间戳取余
        long currentTime = System.currentTimeMillis();
        return currentTime + new Random().nextInt(100);
    }

    /**
     * 减少产品数量
     *
     * @param orderItemList 订单明细
     */
    private void reduceProductStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectProductId(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateById(product);
        }
    }

    /**
     * 清空购车
     *
     * @param cartList 要清空的购物车列表
     */
    private void cleanCart(List<Cart> cartList) {
        for (Cart cart : cartList) {
            cartMapper.deleteById(cart);
        }
    }

    /**
     * 获取订单信息返给前端使用
     *
     * @param order         订单信息
     * @param orderItemList 订单明细
     * @return
     */
    private OrderVo getOrderVo(Order order, List<OrderItem> orderItemList) {

        OrderVo orderVo = OrderVo.builder()
                .orderNo(order.getOrderNo())
                .payment(order.getPayment())
                .paymentType(order.getPaymentType())
                .paymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue())
                .postage(order.getPostage())
                .status(order.getStatus())
                .statusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue())
                .shippingId(order.getShippingId())
                .paymentTime(order.getPaymentTime())
                .sendTime(order.getSendTime())
                .endTime(order.getEndTime())
                .closeTime(order.getCloseTime())
                .createTime(order.getCreateTime())
                .imageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"))

                .build();
        Shipping shipping = shippingMapper.selectById(order.getShippingId());
        if (shipping != null) {
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(getShippingVo(shipping));
        }

        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        for (OrderItem orderItem : orderItemList) {
            OrderItemVo orderItemVo = getOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemList(orderItemVoList);

        return orderVo;
    }

    /**
     * shippingVo
     *
     * @param shipping 收获地址
     * @return
     */
    private ShippingVo getShippingVo(Shipping shipping) {
        ShippingVo shippingVo = ShippingVo.builder()
                .receiverAddress(shipping.getReceiverAddress())
                .receiverCity(shipping.getReceiverCity())
                .receiverDistrict(shipping.getReceiverDistrict())
                .receiverMobile(shipping.getReceiverMobile())
                .receiverName(shipping.getReceiverName())
                .receiverPhone(shipping.getReceiverPhone())
                .receiverZip(shipping.getReceiverZip())
                .receiverProvince(shipping.getReceiverProvince())
                .build();
        return shippingVo;
    }

    /**
     * @param orderItem 订单明细
     * @return
     */
    private OrderItemVo getOrderItemVo(OrderItem orderItem) {
        OrderItemVo orderItemVo = OrderItemVo.builder()
                .orderNo(orderItem.getOrderNo())
                .currentUnitPrice(orderItem.getCurrentUnitPrice())
                .productId(orderItem.getProductId())
                .productImage(orderItem.getProductImage())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .createTime(orderItem.getCreateTime())
                .build();
        return orderItemVo;
    }

    /**
     * 取消订单
     *
     * @param session     session
     * @param orderNumber 订单编号
     * @return
     */
    @Override
    public SR<String> cancel(HttpSession session, Long orderNumber) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        Long userId = ((User) session.getAttribute(Const.CURRENT_USER)).getId();

        Order order = orderMapper.selectByUserIdAndOrderNumber(userId, orderNumber);
        if (order == null) {
            return SR.errorMsg("订单不存在");
        }
        if (order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()) {
            return SR.errorMsg("订单已经付款无法取消订单");
        }
        order.setStatus(Const.OrderStatusEnum.CANCENED.getCode());
        int count = orderMapper.updateById(order);
        if (count > 0) {
            return SR.okMsg("订单取消成功");
        }
        return SR.errorMsg("订单取消失败");

    }

    /**
     * 购物车明细
     *
     * @param session     session
     * @param orderNumber 订单编号
     * @return
     */
    @Override
    public SR getOrderCartProduct(HttpSession session, Long orderNumber) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        Long userId = ((User) session.getAttribute(Const.CURRENT_USER)).getId();

        OrderProductVo orderProductVo = OrderProductVo.builder().build();
        List<Cart> cartList = cartMapper.selectCheckedByUserId(userId);
        SR sr = getCartOrderItem(userId, cartList);
        if (!sr.success()) {
            return sr;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) sr.getData();
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            payment = new BigDecimal(BigDecimalUtil.add(payment.doubleValue(), orderItem.getTotalPrice().doubleValue()));
            orderItemVoList.add(getOrderItemVo(orderItem));
        }

        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));

        return SR.ok(orderProductVo);

    }

    /**
     * 查询订单列表页
     *
     * @param session  session
     * @param pageNum  当前页数
     * @param pageSize 总页数
     * @return
     */
    @Override
    public SR<IPage> getOrderList(HttpSession session, int pageNum, int pageSize) {
        if (!CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        Page page = new Page<>(pageNum, pageSize);
        Long userId = ((User) session.getAttribute(Const.CURRENT_USER)).getId();
        List<Order> orderList = orderMapper.selectOrderListByUserId(page,userId);
        List<OrderListVo> orderListVo = Lists.newArrayList();
        for (Order order : orderList) {
            orderListVo.add(getOrderListVo(order));
        }
        IPage iPage = page.setRecords(orderListVo);
        return SR.ok("查询成功", iPage);
    }

    /**
     * 获取订单列表页
     *
     * @param order 订单
     * @return
     */
    private OrderListVo getOrderListVo(Order order) {
        List<OrderItem> orderItemList = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_no", order.getOrderNo()));
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for (OrderItem orderItem : orderItemList) {
            orderItemVoList.add(getOrderItemVo(orderItem));
        }
        OrderListVo orderListVo = OrderListVo.builder()
                .payment(order.getPayment())
                .status(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue())
                .imageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"))
                .orderItemVoList(orderItemVoList)
                .build();
        return orderListVo;
    }

    /**
     * 获取订单详情
     *
     * @param session     session
     * @param orderNumber 订单号
     * @return
     */
    @Override
    public SR getOrderDetail(HttpSession session, Long orderNumber) {
        if (CheckUser.isLoginSuccess(session)) {
            return SR.error(SRCode.NEED_LOGIN.getCode(), SRCode.NEED_LOGIN.getDesc());
        }
        Long userId = ((User) session.getAttribute(Const.CURRENT_USER)).getId();
        Order order = orderMapper.selectByUserIdAndOrderNumber(userId, orderNumber);
        if (order == null) {
            return SR.errorMsg("该订单不存在");
        }
        List<OrderItem> orderItemList = orderItemMapper.selectOrderItemByOrderNoAndUserId(orderNumber, userId);
        OrderVo orderVo = getOrderVo(order, orderItemList);
        return SR.ok(orderVo);
    }

}
