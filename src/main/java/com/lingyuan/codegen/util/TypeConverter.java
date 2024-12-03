package com.lingyuan.codegen.util;

import com.lingyuan.codegen.model.TableMetadata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeConverter {

    /**
     * 将数据库字段类型转换为 Java 类型
     * @param columnType 数据库字段类型
     * @return 对应的 Java 类型
     */
    public static String convertToJavaType(String columnType) {
        if (columnType == null || columnType.isEmpty()) {
            return "Object";  // 默认返回 Object
        }
        columnType = extractTypeWithLength(columnType).toUpperCase();
        return switch (columnType) {
            case "VARCHAR" -> "String";
            case "INT", "BIGINT" -> "Long";
            case "DECIMAL", "FLOAT", "DOUBLE", "DOUBLE PRECISION", "NUMERIC" -> "BigDecimal";
            case "DATE", "DATETIME", "TIMESTAMP" -> "Date";
            case "BOOLEAN", "BOOL" -> "Boolean";
            default -> "Object";
        };
    }

    /**
     * 将数据库表名（如 user_table）转换为 Java 类名（如 UserTable）
     * @param tableName 数据库表名
     * @return 转换后的 Java 类名
     */
    public static String convertToClassName(String tableName) {
        String[] parts = tableName.split("_");
        StringBuilder className = new StringBuilder();
        for (String part : parts) {
            className.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
        }
        return className.toString();
    }

    /**
     * 将数据库字段类型转换为完整的 Java 类型
     *
     * @param columnType 数据库字段类型
     * @return Java 类型全名（带包路径）
     */
    public static String convertToFullJavaType(String columnType) {
        return switch (columnType.toUpperCase()) {
            case "VARCHAR" -> "java.lang.String";
            case "BIGINT" -> "java.lang.Long";
            case "INT" -> "java.lang.Integer";
            case "DOUBLE" -> "java.lang.DOUBLE";
            case "FLOAT" -> "java.lang.FLOAT";
            case "DECIMAL", "DOUBLE PRECISION" -> "java.math.BigDecimal";
            case "DATE", "DATETIME" -> "Date";
            case "BOOLEAN" -> "Boolean";
            default ->
                    "java.lang.Object";
        };
    }

    public static String getJdbcType(String javaType) {
        return switch (javaType) {
            case "String" -> "VARCHAR";
            case "Integer", "int" -> "INTEGER";
            case "Long", "long" -> "BIGINT";
            case "Date" -> "DATE";
            case "BigDecimal" -> "DECIMAL";
            case "Boolean", "boolean" -> "BIT";
            case "Double", "double" -> "DOUBLE";
            case "Float", "float" -> "FLOAT";
            case "Byte", "byte" -> "TINYINT";
            default -> "OTHER";  // Default jdbcType
        };
    }


    /**
     * 提取需要导入的 Java 类型
     *
     * @param columns 列信息
     * @return 需要导入的 Java 类型集合
     */
    public static Set<String> getImportTypes(List<TableMetadata.ColumnMetadata> columns) {
        Set<String> importTypes = new HashSet<>();
        for (TableMetadata.ColumnMetadata column : columns) {
            String dbType = column.getColumnType();
            String fullJavaType = convertToFullJavaType(dbType);

            // 过滤掉 java.lang 包中的类型（无需显式导入）
            if (!fullJavaType.startsWith("java.lang")) {
                importTypes.add(fullJavaType);
            }
        }
        return importTypes;
    }

    /** 处理带有长度的数据库类型*/
    public static String extractTypeWithLength(String dbType) {
        if (dbType == null || dbType.isEmpty()) {
            return null;
        }

        // 使用正则表达式提取类型名称
        Pattern pattern = Pattern.compile("([A-Za-z]+)\\s*\\((\\d+)(,\\d+)?\\)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(dbType.trim());

        if (matcher.find()) {
            // 返回基础类型（不包括长度）
            return matcher.group(1).toUpperCase();
        }

        // 如果没有长度，直接返回原始类型
        return dbType.toUpperCase();
    }
}
