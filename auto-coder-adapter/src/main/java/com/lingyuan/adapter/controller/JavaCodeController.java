package com.lingyuan.adapter.controller;

import com.lingyuan.api.enums.BusinessTypeEnum;
import com.lingyuan.api.req.BaseReq;
import com.lingyuan.api.resp.Response;
import com.lingyuan.server.service.CoderStrategyFactory;
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

    @RequestMapping("/getMybatisXml")
    @ResponseBody
    public Response<String> getMybatisXml(@RequestBody BaseReq req) {
        return Response.success(CoderStrategyFactory.get(BusinessTypeEnum.MYBATIS_XML.getCode()).execute(req));
    }

    @RequestMapping("/getJavaMapper")
    @ResponseBody
    public Response<String> getJavaMapper(@RequestBody BaseReq req) {
        return Response.success(CoderStrategyFactory.get(BusinessTypeEnum.JAVA_MAPPER.getCode()).execute(req));
    }
}
