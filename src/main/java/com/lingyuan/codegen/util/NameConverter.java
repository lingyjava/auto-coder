package com.lingyuan.codegen.util;

public class NameConverter {

    /**
     * 将下划线命名转换为驼峰命名
     *
     * @param columnName 数据库字段名
     * @return 转换后的驼峰命名字段名
     */
    public static String toCamelCase(String columnName) {
        if (columnName == null || columnName.isEmpty()) {
            return columnName;
        }

        StringBuilder result = new StringBuilder();
        boolean toUpperCase = false;

        for (char c : columnName.toCharArray()) {
            if (c == '_') {
                toUpperCase = true; // 下划线后面字符大写
            } else if (toUpperCase) {
                result.append(Character.toUpperCase(c));
                toUpperCase = false;
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}
