package com.lingyuan.autocoder.api.data;

import com.lingyuan.autocoder.api.model.TableColumnModel;
import com.lingyuan.autocoder.api.model.TableModel;

import lombok.Getter;
import lombok.Setter;

/**
 * @author LingYuan
 */
@Getter
@Setter
public class MybatisXmlData extends BaseData{

    private String namespace;

    private String modelPath;

    private TableColumnModel primaryKeyColumn;

    private TableModel tableModel = new TableModel();
}
