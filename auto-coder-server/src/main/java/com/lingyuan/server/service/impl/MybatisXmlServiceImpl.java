package com.lingyuan.server.service.impl;

import com.google.common.base.CaseFormat;
import com.lingyuan.api.data.MybatisXmlData;
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
        data.setTableModel(tableModel);

        data.setFileName(String.format("%sMapper.xml", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, data.getTableModel().getTableName())));
        data.setNamespace(String.format("%sdao.%sMapper", req.getPackageName() == null ? "" : req.getPackageName() + ".", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, data.getTableModel().getTableName())));
        data.setModelPath(String.format("%smodel.%s", req.getPackageName() == null ? "" : req.getPackageName() + ".", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, data.getTableModel().getTableName())));

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
