package com.lingyuan.autocoder.adapter.controller;

import com.lingyuan.autocoder.api.enums.BusinessTypeEnum;
import com.lingyuan.autocoder.api.req.BaseReq;
import com.lingyuan.autocoder.api.resp.Response;
import com.lingyuan.autocoder.server.service.CoderStrategyFactory;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LingYuan
 */
@RequestMapping("/java")
@RestController
public class JavaCodeController {

    @RequestMapping("/getModel")
    @ResponseBody
    public Response<String> getModel(@RequestBody BaseReq req) {
        return Response.success(CoderStrategyFactory.get(BusinessTypeEnum.JAVA_MODEL.getCode()).execute(req));
    }

    @RequestMapping("/getJavaService")
    @ResponseBody
    public Response<String> getJavaService(@RequestBody BaseReq req) {
        return Response.success(CoderStrategyFactory.get(BusinessTypeEnum.JAVA_SERVICE.getCode()).execute(req));
    }

    @RequestMapping("/getJavaServiceImpl")
    @ResponseBody
    public Response<String> getJavaServiceImpl(@RequestBody BaseReq req) {
        return Response.success(CoderStrategyFactory.get(BusinessTypeEnum.JAVA_SERVICE_IMPL.getCode()).execute(req));
    }

    @RequestMapping("/getJavaMapper")
    @ResponseBody
    public Response<String> getJavaMapper(@RequestBody BaseReq req) {
        return Response.success(CoderStrategyFactory.get(BusinessTypeEnum.JAVA_MAPPER.getCode()).execute(req));
    }

    @RequestMapping("/getMybatisXml")
    @ResponseBody
    public Response<String> getMybatisXml(@RequestBody BaseReq req) {
        return Response.success(CoderStrategyFactory.get(BusinessTypeEnum.MYBATIS_XML.getCode()).execute(req));
    }

}
