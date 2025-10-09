package com.lingyuan.autocoder.common;

/**
 * @author LingYuan
 * @description jdbcType 转换工具
 */
public class JDBCTypeConvertUtil {

    public static String toJava(String type){
        if(type == null || type.trim().isEmpty()) {
            return "String";
        }
        type = type.toLowerCase();

        switch(type){
            case "nvarchar":
            case "varchar":
            case "char":
            case "text":
            case "nchar":
            case "json":
            case "jsonb":
            case "uuid":
            case "clob":
                return "String";
            case "blob":
            case "image":
            case "bytea":
            case "varbinary":
            case "binary":
                return "byte[]";
            case "id":
            case "bigint":
            case "bigserial":
                return "Long";
            case "integer":
            case "int":
            case "tinyint":
            case "mediumint":
            case "smallint":
            case "serial":
                return "Integer";
            case "bit":
            case "boolean":
                return "Boolean";
            case "float":
                return "Float";
            case "double":
                return "Double";
            case "decimal":
            case "numeric":
            case "real":
            case "money":
            case "smallmoney":
            case "number":
                // java.math.BigDecimal
                return "BigDecimal";
            case "date":
            case "datetime":
            case "datetime2":
            case "year":
            case "time":
            case "timestamp":
            case "timestamp with time zone":
            case "timestamp without time zone":
                // java.util.Date
                return "Date";
            default:
                return "String";
        }
    }
}
