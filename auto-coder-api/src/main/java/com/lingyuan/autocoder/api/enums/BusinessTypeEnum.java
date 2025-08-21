package com.lingyuan.autocoder.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LingYuan
 * @description 业务类型枚举
 */
@Getter
@AllArgsConstructor
public enum BusinessTypeEnum {

    JAVA_SERVICE("java-service", "java-service.ftl"),
    JAVA_SERVICE_IMPL("java-service-impl", "java-service-impl.ftl"),
    JAVA_MAPPER("java-mapper", "java-mapper.ftl"),
    MYBATIS_XML("mybatis-xml", "mybatis-xml.ftl"),
    JAVA_MODEL("java-model", "java-model.ftl"),
    ;

    /**
     * 标记
     * */
    private final String code;

    /**
     * 模板名称
     * */
    private final String tmpName;

    public static BusinessTypeEnum getByCode(String code) {
        for (BusinessTypeEnum businessTypeEnum : BusinessTypeEnum.values()) {
            if (businessTypeEnum.getCode().equals(code)) {
                return businessTypeEnum;
            }
        }
        return null;
    }
}
