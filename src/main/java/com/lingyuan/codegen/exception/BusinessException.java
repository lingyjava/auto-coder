package com.lingyuan.codegen.exception;

import java.io.Serial;

/**
 * 自定义业务异常。
 *
 * @author LingYuan
 */
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2326276562198326499L;
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}