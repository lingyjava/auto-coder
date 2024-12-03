package com.lingyuan.codegen.util;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class FreeMarkerUtil {

    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);

    /**
     * 根据模板名称和数据模型生成指定文件内容字符串
     *
     * @param templateName 模板名称
     * @param dataModel    数据模型
     * @return 渲染后的代码字符串
     */
    public static String generate(String templateName, Map<String, Object> dataModel) {
        try {
            return TemplateManager.renderTemplate(templateName, dataModel);
        } catch (IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }
}
