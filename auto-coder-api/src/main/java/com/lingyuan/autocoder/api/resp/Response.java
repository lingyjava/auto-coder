package com.lingyuan.autocoder.api.resp;

import lombok.Data;

import java.io.Serializable;

import com.lingyuan.autocoder.api.enums.ResponseCodeEnum;

/**
 * @author LingYuan
 */
@Data
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -5958269287716214274L;

    /**
     * 状态码
     */
    protected int code;

    /**
     * 响应信息
     */
    protected String message;

    /**
     * 返回数据
     */
    private T data;

    public static <T> Response<T> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(ResponseCodeEnum.FAIL.getCode(), message);
    }

    public Response() {
        this.code = ResponseCodeEnum.SUCCESS.getCode();
        this.message = ResponseCodeEnum.SUCCESS.getMessage();
    }

    public Response(ResponseCodeEnum statusEnums) {
        this.code = statusEnums.getCode();
        this.message = statusEnums.getMessage();
    }

    /**
     * 若没有数据返回，可以人为指定状态码和提示信息
     */
    public Response(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    /**
     * 有数据返回时，状态码为200，默认提示信息为“操作成功！”
     */
    public Response(T data) {
        this.data = data;
        this.code = ResponseCodeEnum.SUCCESS.getCode();
        this.message = ResponseCodeEnum.SUCCESS.getMessage();
    }

    /**
     * 有数据返回，状态码为 200，人为指定提示信息
     */
    public Response(T data, String msg) {
        this.data = data;
        this.code = ResponseCodeEnum.SUCCESS.getCode();
        this.message = msg;
    }

}
