package com.lingyuan.autocoder.common;

/**
 * @author LingYuan
 * @description jdbcType 转换工具
 */
public class JDBCTypeConvertUtil {

    public static String toJava(String type){
        if(type == null || type.trim().isEmpty()) {
            return null;
        }
        type = type.toLowerCase();

        switch(type){
            case "nvarchar":
            case "varchar":
            case "char":
            case "text":
            case "nchar":
                return "String";
            case "blob":
            case "image":
                return "byte[]";
            case "id":
            case "bigint":
                return "Long";
            case "integer":
            case "tinyint":
            case "mediumint":
            case "smallint":
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
                // java.math.BigDecimal
                return "BigDecimal";
            case "date":
            case "datetime":
            case "year":
            case "time":
            case "timestamp":
                // java.util.Date
                return "Date";
            default:
                return null;
        }
    }
}
