package com.lingyuan.autocoder.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author LingYuan
 */
@Getter
@Setter
@ToString
public class TableModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 库名
     * */
    private String databaseName;

    /**
     * 表名
     * */
    private String tableName;

    /**
     * 表注释
     * */
    private String tableDescription;

    /**
     * 属性列
     * */
    private List<TableColumnModel> columns;

}
