package com.lingyuan.codegen.exception;


import com.lingyuan.codegen.resp.ApiResponse;
import net.sf.jsqlparser.JSQLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * 全局异常处理类。
 *
 * @author LingYuan
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理非法参数异常。
     *
     * @param e IllegalArgumentException
     * @return 包装的响应信息
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * SQL 解析异常
     * @param e JSQLParserException
     * @return 包装的响应信息
     */
    @ExceptionHandler(JSQLParserException.class)
    public ApiResponse<Void> handleJSQLParserException(JSQLParserException e) {
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * IO 异常
     * @param e IOException
     * @return 包装的响应信息
     */
    @ExceptionHandler(IOException.class)
    public ApiResponse<Void> handleIOException(IOException e) {
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    /**
     * 自定义业务异常
     * @param e BusinessException
     * @return 包装的响应信息
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(BusinessException e) {
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理未捕获的其他异常。
     *
     * @param e Exception
     * @return 包装的响应信息
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        logger.error("Unhandled exception: ", e);
        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");
    }

}