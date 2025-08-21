package com.lingyuan.autocoder.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LingYuan
 */
@Getter
@AllArgsConstructor
public enum DatabaseTypeEnum {
    MYSQL("MySQL"),
    ORACLE("Oracle"),
    POSTGRESQL("PostgresQL"),
    ;

    private final String name;
}
