package com.lingyuan.autocoder.api.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * @author LingYuan
 */
@Getter
@Setter
@ToString
public class JavaMapperData extends BaseData{

    private String packageName;

    private String className;

    private Set<String> importPackPath;

    private String modelName;

    private String classDescription;

    private String author;
}
