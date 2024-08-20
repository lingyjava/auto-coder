package com.lingyuan.server.service.impl;

import com.google.common.base.CaseFormat;
import com.lingyuan.api.data.JavaMapperData;
import com.lingyuan.api.data.JavaModelData;
import com.lingyuan.api.enums.BusinessTypeEnum;
import com.lingyuan.api.exception.BusinessException;
import com.lingyuan.api.model.TableModel;
import com.lingyuan.api.req.BaseReq;
import com.lingyuan.common.util.SQLParseUtil;
import com.lingyuan.server.config.FreeMarkerConfig;
import com.lingyuan.server.service.AutoCoderService;
import com.lingyuan.server.service.CoderStrategyFactory;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author LingYuan
 */
@Service
public class JavaMapperServiceImpl implements AutoCoderService {

    @Override
    public String execute(BaseReq req) {
        if (req == null) {
            throw new BusinessException("入参为空");
        }
        if (req.getDdl() == null || req.getDdl().isEmpty()) {
            throw new BusinessException("DDL为空");
        }
        Configuration config = FreeMarkerConfig.getConfiguration();
        JavaMapperData data = this.getData(req);
        try {
            Template template = config.getTemplate(BusinessTypeEnum.JAVA_MAPPER.getTmpName());
            StringWriter out = new StringWriter();
            template.process(data, out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            throw new BusinessException(String.format(e.getLocalizedMessage()));
        }
    }

    private JavaMapperData getData(BaseReq req) {
        JavaMapperData data = new JavaMapperData();
        TableModel tableModel = SQLParseUtil.buildTableModel(SQLParseUtil.parseDDL(req.getDdl()));
        data.setModelName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableModel.getTableName()));
        data.setClassName(String.format("%sMapper", data.getModelName()));
        data.setFileName(String.format("%s.java", data.getClassName()));
        data.setPackageName(req.getPackageName() != null && !req.getPackageName().isEmpty() ? req.getPackageName() + ".mapper" : "mapper");
        data.setClassDescription(tableModel.getTableName() + "数据访问层");
        data.setAuthor(req.getAuthor() == null || req.getAuthor().isEmpty() ? "AutoCoder" : req.getAuthor());
        Set<String> imports = new HashSet<>();
        imports.add(req.getPackageName() != null && !req.getPackageName().isEmpty() ? req.getPackageName() + ".model." + data.getModelName() : "model." + data.getModelName());
        imports.add("java.util.List");
        data.setImportPackPath(imports);
        return data;
    }

    @PostConstruct
    public void init() {
        CoderStrategyFactory.register(BusinessTypeEnum.JAVA_MAPPER.getCode(), this);
    }
}
