package ${packageName};

<#list importPackPath as path>
import ${path};
</#list>

/**
* <#if tableModel?? && tableModel.databaseName??>${tableModel.databaseName}.</#if>${tableModel.tableName} Model
* @author: ${author}
* */
<#if classAnnotation??>
<#list classAnnotation as annotation>
${annotation}
</#list>
</#if>
public class ${className} <#if implementsClass?? && (implementsClass?size > 0)>implements <#list implementsClass as ic>${ic}<#sep>, </#sep></#list></#if>{

<#list tableModel.columns as column>
    /**
    * ${column.columnComment}<#if column.primaryKey>(主键)</#if>
    * */
    private ${column.fieldType} ${column.fieldName};

</#list>
<#if !lombok>
<#list tableModel.columns as column>
    public ${column.fieldType} get${column.fieldName?cap_first}() { return ${column.fieldName}; }
    public void set${column.fieldName?cap_first}(${column.fieldType} ${column.fieldName}) { this.${column.fieldName} = ${column.fieldName}; }

</#list>
</#if>
}