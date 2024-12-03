package com.lingyuan.codegen.util;


import com.lingyuan.codegen.model.TableMetadata;
import com.lingyuan.codegen.model.TableMetadata.ColumnMetadata;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLParserUtil {

    /**
     * 解析 SQL 创建表语句，生成 TableMetadata。
     *
     * @param ddl SQL 创建表语句
     * @return 表的元数据
     */
    public static TableMetadata parseCreateTable(String ddl) throws JSQLParserException {
        // 使用 JSQLParser 解析 SQL
        CreateTable createTable = (CreateTable) CCJSqlParserUtil.parse(ddl);

        // 提取表名
        String tableName = createTable.getTable().getName();

        // 提取列信息
        List<ColumnMetadata> columns = new ArrayList<>();

        for (ColumnDefinition columnDef : createTable.getColumnDefinitions()) {
            String columnName = columnDef.getColumnName();
            String columnType = columnDef.getColDataType().getDataType();

            // 默认值和注释提取
            String defaultValue = null;
            String comment = null;
            boolean nullable = true;
            List<String> specs = columnDef.getColumnSpecs();
            if (specs != null) {
                String specsString = String.join(" ", specs);
                nullable = !specsString.toUpperCase().contains("NOT NULL");

                for (String spec : specs) {
                    switch (spec.toUpperCase()) {
                        case "DEFAULT":
                            defaultValue = extractDefaultValue(specsString);
                            break;
                        case "COMMENT":
                            comment = extractComment(specsString);
                            break;
                        default:
                            break;
                    }
                }
            }
            // 构造 ColumnMetadata 对象
            columns.add(new ColumnMetadata(columnName, columnType, nullable, defaultValue, comment));
        }

        // 主键信息
        List<Index> indexes = createTable.getIndexes();
        if (indexes != null) {
            for (Index index : indexes) {
                if ("PRIMARY KEY".equalsIgnoreCase(index.getType())) {
                    for (String primaryKeyColumn : index.getColumnsNames()) {
                        for (ColumnMetadata column : columns) {
                            if (column.getColumnName().equalsIgnoreCase(primaryKeyColumn)) {
                                column.setPrimaryKey(true);
                                column.setNullable(false);
                            }
                        }
                    }
                }
            }
        }

        columns.forEach(c -> {
            // set javaType
            c.setJavaType(TypeConverter.convertToJavaType(c.getColumnType()));

            // set fieldName
            c.setFieldName(NameConverter.toCamelCase(c.getColumnName()));

            // set jdbcType
            c.setJdbcType(TypeConverter.getJdbcType(c.getJavaType()));
        });

        // 返回表元数据
        return new TableMetadata(tableName, columns);
    }

    // 提取 DEFAULT 值
    private static String extractDefaultValue(String specString) {
        Pattern defaultPattern = Pattern.compile("DEFAULT\\s+([\\w'\"\\d]+)");
        Matcher matcher = defaultPattern.matcher(specString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // 提取 COMMENT 值
    private static String extractComment(String specString) {
        Pattern commentPattern = Pattern.compile("COMMENT\\s+'([^\']+)'");
        Matcher matcher = commentPattern.matcher(specString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
