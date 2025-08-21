package com.lingyuan.autocoder.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.lingyuan.autocoder.api.resp.Response;

/**
 * @author LingYuan
 */
@Slf4j
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("supports:{}", returnType.getDeclaringClass().getName());
        /**
         * 排除swagger-ui请求返回数据增强
         */
        return !returnType.getDeclaringClass().getName().contains("springfox");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // ResponseInfo类型直接返回
        if (body instanceof Response) {
            return body;
        }
        // 空，返回成功
        else if (body == null) {
            return Response.success();
        }
        // 异常类型直接返回
        else if (body instanceof Exception) {
            return body;
        }
        // String类型直接返回
        else if (body instanceof String) {
            return body;
        }
        // 返回封装后的数据
        else {
            return Response.success(body);
        }
    }

}
