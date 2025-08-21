package com.lingyuan.autocoder.api.exception;

import lombok.Getter;

/**
 * @author LingYuan
 * @description 业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 异常返回码
     * */
    private String code ;

    /**
     * 异常对应的描述信息
     * */
    private String msg;

    public BusinessException() {
        super();
    }

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

}
