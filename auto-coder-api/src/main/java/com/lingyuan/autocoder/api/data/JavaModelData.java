package com.lingyuan.autocoder.api.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

import com.lingyuan.autocoder.api.model.TableModel;

/**
 * @author LingYuan
 */
@Getter
@Setter
@ToString
public class JavaModelData extends BaseData{

    /**
     * 类名
     * */
    private String className;

    /**
     * 包名
     * */
    private String packageName;

    /**
     * 需导入的包
     * */
    private Set<String> importPackPath;

    /**
     * 实现的类/接口
     * */
    private Set<String> implementsClass;

    /**
     * 类注解
     * */
    private Set<String> classAnnotation;

    private TableModel tableModel;

    private boolean lombok = false;

    private String author;
}
