package com.lingyuan.autocoder.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author LingYuan
 */
@Getter
@Setter
@ToString
public class TableColumnModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 列 - 数据类型
     * */
    private String columnType;

    /**
     * 列 - 名称
     * */
    private String columnName;

    /**
     * 列 - 注释
     * */
    private String columnComment;

    /**
     * 是否主键
     * */
    private Boolean primaryKey;

    /**
     * 字段属性名称
     * columnName 下划线转驼峰
     * */
    private String fieldName;

    /**
     * 字段属性类型
     * columnType 转字段类型
     * */
    private String fieldType;
}
