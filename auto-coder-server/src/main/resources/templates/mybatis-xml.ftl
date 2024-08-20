<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${namespace}">

    <resultMap type="${modelPath}" id="BaseResultMap">
        <#list tableModel.columns as column>
        <result property="${column.fieldName}" column="${column.columnName}" jdbcType="${column.columnType?upper_case}" />
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list tableModel.columns as column>
        ${column.columnName}<#sep>, </#sep>
        </#list>
    </sql>

    <!-- 通过主键查询 -->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM ${tableModel.tableName}
        <#if primaryKeyColumn??>
        WHERE ${primaryKeyColumn.columnName} = <#noparse>#</#noparse>{${primaryKeyColumn.fieldName}, jdbcType=${primaryKeyColumn.columnType?upper_case}}
        <#else>
        <!-- 未找到主键，请确认是否设置主键 -->
        </#if>
    </select>

    <!-- 通过实体类查询 -->
    <select id="selectByModel" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM ${tableModel.tableName}
        <where>
            <#list tableModel.columns as column>
            <#noparse><if</#noparse> test="${column.fieldName} != null<#if column.fieldType == "String"> and ${column.fieldName} != ''</#if>">
                ${column.columnName} = <#noparse>#</#noparse>{${column.fieldName}, jdbcType=${column.columnType?upper_case}}
            <#noparse></if></#noparse>
            </#list>
        </where>
    </select>

    <!-- 新增 -->
    <insert id="insert" keyProperty="<#if primaryKeyColumn??>${primaryKeyColumn.fieldName}</#if>" useGeneratedKeys="true">
        INSERT INTO ${tableModel.tableName} (
            <#list tableModel.columns as column>
            ${column.columnName}<#sep>, </#sep>
            </#list>
        )
        VALUES (
            <#list tableModel.columns as column>
            <#noparse>#</#noparse>{${column.fieldName}, jdbcType=${column.columnType?upper_case}}<#sep>, </#sep>
            </#list>
        )
    </insert>

    <!-- 新增多条 -->
    <insert id="insertList" keyProperty="<#if primaryKeyColumn??>${primaryKeyColumn.fieldName}</#if>" useGeneratedKeys="true">
        INSERT INTO ${tableModel.tableName} (
        <#list tableModel.columns as column>
            ${column.columnName}<#sep>, </#sep>
        </#list>
        )
        VALUES
        <foreach collection="list" item="item" separator="," open="(" close=")">
        <#list tableModel.columns as column>
            <#noparse>#</#noparse>{item.${column.fieldName}, jdbcType=${column.columnType?upper_case}}<#sep>, </#sep>
        </#list>
        </foreach>
    </insert>

    <!-- 全量更新 -->
    <update id="update">
        UPDATE ${tableModel.tableName}
        SET
            <#list tableModel.columns as column>
            ${column.columnName} = <#noparse>#</#noparse>{${column.fieldName}, jdbcType=${column.columnType?upper_case}}<#sep>, </#sep>
            </#list>
        <#if primaryKeyColumn??>WHERE ${primaryKeyColumn.columnName} = <#noparse>#</#noparse>{${primaryKeyColumn.fieldName}, jdbcType=${primaryKeyColumn.columnType?upper_case}}
        <#else>
        <!-- 未找到主键，请确认是否设置主键 -->
        </#if>
    </update>

    <!-- 按选择更新 -->
    <update id="updateBySelective">
        UPDATE ${tableModel.tableName}
        <set>
            <#list tableModel.columns as column>
            <#noparse><if</#noparse> test="${column.fieldName} != null<#if column.fieldType == 'String'> and ${column.fieldName} != ''</#if>" >
                ${column.columnName} = <#noparse>#</#noparse>{${column.fieldName}, jdbcType=${column.columnType?upper_case}},
            <#noparse></if></#noparse>
            </#list>
        </set>
        <#if primaryKeyColumn??>WHERE ${primaryKeyColumn.columnName} = <#noparse>#</#noparse>{${primaryKeyColumn.fieldName}, jdbcType=${primaryKeyColumn.columnType?upper_case}}
        <#else>
            <!-- 未找到主键，请确认是否设置主键 -->
        </#if>
    </update>

    <!-- 删除 -->
    <delete id="delete">
        DELETE FROM ${tableModel.tableName} <#if primaryKeyColumn??>WHERE ${primaryKeyColumn.columnName} = <#noparse>#</#noparse>{${primaryKeyColumn.fieldName}, jdbcType=${primaryKeyColumn.columnType?upper_case}}<#else><!-- 未找到主键，请确认是否设置主键 --></#if>
    </delete>

</mapper>