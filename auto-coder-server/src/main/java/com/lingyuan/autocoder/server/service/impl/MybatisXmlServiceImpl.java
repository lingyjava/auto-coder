package com.lingyuan.autocoder.server.service.impl;

import com.google.common.base.CaseFormat;
import com.lingyuan.autocoder.api.data.MybatisXmlData;
import com.lingyuan.autocoder.api.enums.BusinessTypeEnum;
import com.lingyuan.autocoder.api.exception.BusinessException;
import com.lingyuan.autocoder.api.model.TableColumnModel;
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

/**
 * @author LingYuan
 */
@Service
public class MybatisXmlServiceImpl implements AutoCoderService {

    @Override
    public String execute(BaseReq req) {
        if (req == null) {
            throw new BusinessException("入参为空");
        }
        if (req.getDdl() == null || req.getDdl().isEmpty()) {
            throw new BusinessException("DDL为空");
        }
        Configuration config = FreeMarkerConfig.getConfiguration();
        MybatisXmlData data = this.getData(req);
        try {
            Template template = config.getTemplate(BusinessTypeEnum.MYBATIS_XML.getTmpName());
            StringWriter out = new StringWriter();
            template.process(data, out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            throw new BusinessException(String.format(e.getLocalizedMessage()));
        }
    }

    private MybatisXmlData getData(BaseReq req) {
        MybatisXmlData data = new MybatisXmlData();

        TableModel tableModel = SQLParseUtil.buildTableModel(SQLParseUtil.parseDDL(req.getDdl(), req.getDbType()));
        if (tableModel == null || tableModel.getTableName() == null || tableModel.getTableName().isEmpty()) {
            throw new BusinessException("解析DDL失败，无法生成表模型");
        }
        data.setTableModel(tableModel);

        String className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableModel.getTableName());
        data.setFileName(String.format("%sMapper.xml", className));
        data.setNamespace(String.format("%smapper.%sMapper", req.getPackageName() == null ? "" : req.getPackageName() + ".", className));
        data.setModelPath(String.format("%smodel.%s", req.getPackageName() == null ? "" : req.getPackageName() + ".", className));

        for (TableColumnModel columnModel : data.getTableModel().getColumns()) {
            if (columnModel.getPrimaryKey()) {
                data.setPrimaryKeyColumn(columnModel);
            }
        }
        return data;
    }

    @PostConstruct
    public void init() {
        CoderStrategyFactory.register(BusinessTypeEnum.MYBATIS_XML.getCode(), this);
    }
}
