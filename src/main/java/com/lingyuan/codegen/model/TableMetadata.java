package com.lingyuan.codegen.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TableMetadata {

    private String tableName;
    private List<ColumnMetadata> columns;

    @Setter
    @Getter
    @ToString
    @AllArgsConstructor
    public static class ColumnMetadata {
        /** 数据库字段名*/
        private String columnName;
        /** 数据库字段类型*/
        private String columnType;
        /** 驼峰格式的字段名*/
        private String fieldName;
        private boolean primaryKey;
        private boolean nullable;
        // 默认值
        private String defaultValue;
        /** 注释*/
        private String comment;
        private String javaType;
        private String jdbcType;

        public ColumnMetadata(String columnName, String columnType, boolean nullable, String defaultValue, String comment) {
            this.columnName = columnName;
            this.columnType = columnType;
            this.nullable = nullable;
            this.defaultValue = defaultValue;
            this.comment = comment;
        }
    }

}
