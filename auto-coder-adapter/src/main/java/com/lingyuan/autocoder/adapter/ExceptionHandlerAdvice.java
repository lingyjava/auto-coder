package com.lingyuan.autocoder.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lingyuan.autocoder.api.exception.BusinessException;
import com.lingyuan.autocoder.api.resp.Response;

import java.nio.file.AccessDeniedException;

/**
 * @author LingYuan
 * @description 全局异常处理器
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    /**
     * 参数格式异常处理
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<String> badRequestException(IllegalArgumentException ex) {
        logger.error("参数格式不合法：{}", ex.getMessage());
        return new Response<>(HttpStatus.BAD_REQUEST.value() + "", "参数格式不符！");
    }

    /**
     * 权限不足异常处理
     */
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Response<String> badRequestException(AccessDeniedException ex) {
        return new Response<>(HttpStatus.FORBIDDEN.value() + "", ex.getMessage());
    }

    /**
     * 参数缺失异常处理
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<String> badRequestException(Exception ex) {
        return new Response<>(HttpStatus.BAD_REQUEST.value() + "", "缺少必填参数！");
    }

    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> handleTypeMismatchException(NullPointerException ex) {
        logger.error("空指针异常，详细信息：", ex);
        return Response.fail("空指针异常: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> handleUnexpectedServer(Exception ex) {
        logger.error("系统异常：", ex);
        return Response.fail("系统发生异常，请联系管理员");
    }

    /**
     * 系统异常处理
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<String> exception(Throwable throwable) {
        logger.error("系统异常", throwable);
        return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value() + "系统异常，请联系管理员！");
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Response<String> handleBusinessException(BusinessException ex) {
        logger.error("业务异常：", ex);
        return Response.fail("业务异常：" + ex.getMessage());
    }

}


