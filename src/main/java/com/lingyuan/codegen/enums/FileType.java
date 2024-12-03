package com.lingyuan.codegen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {

    MODEL("Model", "model.ftl", "{className}.java"),
    MAPPER("Mapper", "mapper.ftl", "{className}Mapper.java"),
    MAPPER_XML("MapperXml", "mapper_xml.ftl", "{className}Mapper.xml"),
    SERVICE("Service", "service.ftl", "{className}Service.java"),
    SERVICE_IMPL("ServiceImpl", "service_impl.ftl", "{className}ServiceImpl.java"),
    CONTROLLER("Controller", "controller.ftl", "{className}Controller.java");

    private final String type;
    /** 模板路径*/
    private final String templatePath;
    /** 文件名称模板*/
    private final String fileNameTemplate;

    public static FileType getFileType(String type) {
        for (FileType fileType : FileType.values()) {
            if (fileType.type.equalsIgnoreCase(type)) {
                return fileType;
            }
        }
        return null;
    }
}
