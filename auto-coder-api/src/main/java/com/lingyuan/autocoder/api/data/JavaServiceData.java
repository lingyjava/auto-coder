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
public class JavaServiceData extends BaseData{

    private String packageName;

    private Set<String> importPackPath;

    private String classDescription;

    private String author;

    private String className;

    private String modelName;
}
