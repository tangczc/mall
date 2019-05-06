package com.rootchen.mall.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;


/**
 * @Title: RS.java
 * @Package: com.xxx.common
 * @author: LiChen
 * @create: 2019-04-19 13:27
 */

//保证序列化json的时候，如果是null的对象，key也会消失
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) 过期不推荐使用
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SR<T> implements Serializable {
    //响应状态码
    private Integer status;
    //响应消息
    private String msg;
    //响应数据
    private T data;

    private SR(Integer status){
        this.status = status;
    }

    private SR(Integer status,String msg){
        this.status = status;
        this.msg = msg;
    }

    private SR(Integer status,T data){
        this.data = data;
        this.status = status;
    }

    private SR(Integer status,String msg,T data){
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    //使之不再json序列化中
    @JsonIgnore
    public boolean success(){
        return this.status == SRCode.SUCCESS.getCode();
    }

    public Integer getStatus(){
        return status;
    }

    public T getData(){
        return data;
    }

    public String getMsg(){
        return msg;
    }

    public static <T> SR<T> ok(){
        return new SR<T>(SRCode.SUCCESS.getCode());
    }

    public static <T> SR<T> ok(T data){
        return new SR<T>(SRCode.SUCCESS.getCode(),SRCode.SUCCESS.getDesc(),data);
    }


    public static <T> SR<T> ok(String msg ,T data){
        return new SR<T>(SRCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> SR<T> error(){
        return new SR<T>(SRCode.ERROR.getCode(),SRCode.ERROR.getDesc());
    }

    public static <T> SR<T> error(T data){
        return new SR<T>(SRCode.ERROR.getCode(),SRCode.ERROR.getDesc(),data);
    }

    public static <T> SR<T> error(Integer code,String msg){
        return new SR<T>(code,msg);
    }

}