package com.lingyuan.autocoder.server.service.impl;

import com.google.common.base.CaseFormat;
import com.lingyuan.autocoder.api.data.JavaServiceImplData;
import com.lingyuan.autocoder.api.enums.BusinessTypeEnum;
import com.lingyuan.autocoder.api.exception.BusinessException;
import com.lingyuan.autocoder.api.model.TableModel;
import com.lingyuan.autocoder.api.req.BaseReq;
import com.lingyuan.autocoder.common.SQLParseUtil;
import com.lingyuan.autocoder.server.config.FreeMarkerConfig;
import com.lingyuan.autocoder.server.service.AutoCoderService;
import com.lingyuan.autocoder.server.service.CoderStrategyFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author LingYuan
 * @desciption Java-Service-Impl模板实现
 * */
@Service
public class JavaSIServiceImpl implements AutoCoderService {

    @Override
    public String execute(BaseReq req) {
        if (req == null) {
            throw new BusinessException("入参为空");
        }
        if (req.getDdl() == null || req.getDdl().isEmpty()) {
            throw new BusinessException("DDL为空");
        }
        Configuration config = FreeMarkerConfig.getConfiguration();
        JavaServiceImplData data = this.getData(req);
        try {
            Template template = config.getTemplate(BusinessTypeEnum.JAVA_SERVICE_IMPL.getTmpName());
            StringWriter out = new StringWriter();
            template.process(data, out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            throw new BusinessException(String.format(e.getLocalizedMessage()));
        }
    }

    private JavaServiceImplData getData(BaseReq req) {
        JavaServiceImplData data = new JavaServiceImplData();
        TableModel tableModel = SQLParseUtil.buildTableModel(SQLParseUtil.parseDDL(req.getDdl()));
        boolean packageNameIsEmpty = req.getPackageName() == null || req.getPackageName().isEmpty();

        data.setModelName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableModel.getTableName()));
        data.setClassName(String.format("%sServiceImpl", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableModel.getTableName())));
        data.setPackageName(String.format("%sservice.impl", packageNameIsEmpty ? "" : req.getPackageName() + "."));
        data.setClassDescription(data.getModelName() + "服务层实现");
        data.setAuthor(req.getAuthor() == null || req.getAuthor().isEmpty() ? "AutoCoder" : req.getAuthor());
        data.setFileName(String.format("%s.java", data.getClassName()));

        Set<String> imports = new LinkedHashSet<>();
        imports.add(packageNameIsEmpty ? "model." + data.getModelName() : req.getPackageName() + ".model." + data.getModelName());
        imports.add(packageNameIsEmpty ? "service." + data.getModelName() + "Service" : req.getPackageName() + ".service." + data.getModelName() + "Service");
        imports.add("org.springframework.stereotype.Service");
        imports.add("org.springframework.beans.factory.annotation.Autowired");
        data.setImportPackPath(imports);

        data.setServiceName(data.getModelName() + "Service");
        data.setMapperName(data.getModelName() + "Mapper");

        return data;
    }

    @PostConstruct
    public void init() {
        CoderStrategyFactory.register(BusinessTypeEnum.JAVA_SERVICE_IMPL.getCode(), this);
    }
}
