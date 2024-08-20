package com.lingyuan.server.service.impl;

import com.google.common.base.CaseFormat;
import com.lingyuan.api.data.JavaModelData;
import com.lingyuan.api.enums.BusinessTypeEnum;
import com.lingyuan.api.exception.BusinessException;
import com.lingyuan.api.model.TableColumnModel;
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
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author LingYuan
 * java-model 生成
 */
@Service
public class JavaModelServiceImpl implements AutoCoderService {

    @Override
    public String execute(BaseReq req) {
        if (req == null) {
            throw new BusinessException("入参为空");
        }
        if (req.getDdl() == null || req.getDdl().isEmpty()) {
            throw new BusinessException("DDL为空");
        }
        Configuration config = FreeMarkerConfig.getConfiguration();
        JavaModelData data = this.getData(req);
        try {
            Template template = config.getTemplate(BusinessTypeEnum.JAVA_MODEL.getTmpName());
            StringWriter out = new StringWriter();
            template.process(data, out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            throw new BusinessException(String.format(e.getLocalizedMessage()));
        }
    }

    private JavaModelData getData(BaseReq req) {
        JavaModelData data = new JavaModelData();
        data.setAuthor(req.getAuthor() == null || req.getAuthor().isEmpty() ? "AutoCoder" : req.getAuthor());

        TableModel tableModel = SQLParseUtil.buildTableModel(SQLParseUtil.parseDDL(req.getDdl(), req.getDbType()));
        data.setTableModel(tableModel);

        data.setClassName(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableModel.getTableName()));
        data.setFileName(String.format("%s.java", data.getClassName()));
        data.setPackageName(String.format("%smodel.%s", req.getPackageName() == null || req.getPackageName().isEmpty() ? "" : req.getPackageName() + ".", data.getClassName()));

        // 需导入的包
        Set<String> importPackPath = new LinkedHashSet<>();
        // 实现的类/接口
        Set<String> implementsClass = new LinkedHashSet<>();
        // 类注解
        Set<String> classAnnotation = new LinkedHashSet<>();

        if (tableModel.getColumns() != null && !tableModel.getColumns().isEmpty()) {
            for (TableColumnModel column : tableModel.getColumns()) {
                switch (column.getFieldType()) {
                    case "BigDecimal" :
                        importPackPath.add("java.math.BigDecimal");
                        break;
                    case "Date" :
                        importPackPath.add("java.util.Date");
                        break;
                    default:
                }
            }
        }

        if (req.isSerializable()) {
            importPackPath.add("java.io.Serializable");
            implementsClass.add("Serializable");
        }

        if (req.isLombok()) {
            data.setLombok(true);
            importPackPath.add("lombok.Getter");
            importPackPath.add("lombok.Setter");
            importPackPath.add("lombok.ToString");
            classAnnotation.add("@Getter");
            classAnnotation.add("@Setter");
            classAnnotation.add("@ToString");
        }

        data.setImportPackPath(importPackPath);
        data.setImplementsClass(implementsClass);
        data.setClassAnnotation(classAnnotation);
        return data;
    }

    @PostConstruct
    public void init() {
        CoderStrategyFactory.register(BusinessTypeEnum.JAVA_MODEL.getCode(), this);
    }

}
