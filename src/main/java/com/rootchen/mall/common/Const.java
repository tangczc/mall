package com.rootchen.mall.common;

import com.google.common.collect.Sets;

import java.util.Set;

public class Const {
    public static final String CURRENT_USER = "current_user";
    public static final String EMAIL = "email";
    public static final String USERNAME = "userName";


    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc", "price_asc");
    }

    public interface Cart {
        int CHECKED = 1;//购物车选中
        int UN_CHECKED = 0;//购物车未选中
        String LIMIT_NUM_FILE = "LIMIT_NUM_FILE";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface Role {
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;    //管理员

    }

    public interface Email{
        int EMAIL_ACTIVATE = 1; //邮箱激活
        int EMAIL_INACTIVATE = 0; //邮箱未激活
    }

    public enum ProductStatusEnum {
        ON_SALE(1, "在线");
        private String value;
        private int code;

        ProductStatusEnum(int code, String value) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

    }

    public enum PayPlatformEnum{
        AILIPAY(1,"支付宝");

        private int code;
        private String value;

        PayPlatformEnum(int code, String  value){
            this.code = code;
            this.value = value;
        }
        public int getCode(){
            return code;
        }
        public String getValue(){
            return value;
        }
    }


    public enum OrderStatusEnum{
        NO_PAY(10,"未付款"),
        CANCENED(0,"已取消"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");


        private int code;
        private String value;

        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public int getCode(){
            return code;
        }
        public String getValue(){
            return value;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum: values()){
                if (orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }

    }

    public enum PaymentTypeEnum{

        ONLINE_PAY(1," 在线支付");

        private int code;
        private String value;
        PaymentTypeEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public int getCode(){
            return code;
        }
        public String getValue(){
            return value;
        }

        public static PaymentTypeEnum codeOf(int code){
            for (PaymentTypeEnum paymentTypeEnum : values()){
                if (paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }


    }
}
