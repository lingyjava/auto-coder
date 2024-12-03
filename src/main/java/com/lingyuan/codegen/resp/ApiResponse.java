package com.lingyuan.codegen.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用 API 响应包装类。
 *
 * @author LingYuan
 * @param <T> 数据类型
 */
@Setter
@Getter
public class ApiResponse<T> {

    /**状态码*/
    private int code;
    /**消息*/
    private String message;
    /**数据*/
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 快速生成响应
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

}