package com.lingyuan.autocoder.server.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author LingYuan
 * @description FreeMarker 模板配置
 */
public class FreeMarkerConfig {

    public static Configuration getConfiguration() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setClassLoaderForTemplateLoading(Thread.currentThread().getContextClassLoader(), "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        return cfg;
    }
}
