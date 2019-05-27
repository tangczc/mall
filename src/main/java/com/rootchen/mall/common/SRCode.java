package com.rootchen.mall.common;

/**
 * @Tile: SRCode
 * @author: LiChen
 * @create: 2019-04-19 13:44
 * @description: 自定义响应数据结构：
 * 200:表示成功
 * 500:表示错误，错误信息存在msg字段中
 * 501:bean验证错误，不管有多少个错误都已map形式返回
 * 502:拦截器拦截到用户token错误
 * 555:异常抛出信息
 * 10:用户未登陆
 * 2:不是管理员权限
 */
public enum SRCode {

    SUCCESS(200, "SUCCESS"),
    ERROR(500, "ERROR"),
    BEAN_ERROR(501, "BEAN_ERROR"),
    TOKEN_ERROR(502, "TOKEN_ERROR"),
    EXCEPTION_INFO(555, "EXCEPTION_INFO"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final Integer code;
    private final String desc;

    SRCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}