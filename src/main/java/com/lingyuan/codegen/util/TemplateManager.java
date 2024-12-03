package com.lingyuan.codegen.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class TemplateManager {

    private static final Logger logger = LoggerFactory.getLogger(TemplateManager.class);

    private static final Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
    private static final String TEMPLATE_DIRECTORY = "/templates";

    // 静态代码块加载模板配置
    static {
        // 设置模板加载路径
        configuration.setClassLoaderForTemplateLoading(
                TemplateManager.class.getClassLoader(), TEMPLATE_DIRECTORY
        );
        // 设置默认编码格式
        configuration.setDefaultEncoding("UTF-8");
    }

    /**
     * 获取模板对象
     *
     * @param templateName 模板文件名
     * @return 模板对象
     */
    public static Template getTemplate(String templateName) throws IOException {
        return configuration.getTemplate(templateName);
    }

    /**
     * 渲染模板
     *
     * @param templateName 模板文件名
     * @param dataModel    数据模型
     * @return 渲染后的代码字符串
     */
    public static String renderTemplate(String templateName, Map<String, Object> dataModel) throws IOException, TemplateException {
        Template template = getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }
}
