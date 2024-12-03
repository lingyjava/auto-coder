package ${basePackage}.model;

<#if importTypes?has_content>
<#list importTypes as importType>
import ${importType};
</#list>

</#if>
<#if useSerializable>
import java.io.Serializable;

</#if>
<#if useLombok>
import lombok.Data;

</#if>
/**
* Model for ${tableName}
*
* @author: ${author}
*/
<#if useLombok>
@Data
</#if>
public class ${className} <#if useSerializable>implements Serializable</#if> {
<#if useSerializable>

    private static final long serialVersionUID = 1L;
</#if>
<#list columns as column>

    private ${column.javaType} ${column.fieldName};
</#list>
<#if !useLombok>
<#list columns as column>

    public ${column.javaType} get${column.fieldName?cap_first}() {
        return ${column.fieldName};
    }
    public void set${column.fieldName?cap_first}(${column.javaType} ${column.fieldName}) {
        this.${column.fieldName} = ${column.fieldName};
    }
</#list>
</#if>
}
