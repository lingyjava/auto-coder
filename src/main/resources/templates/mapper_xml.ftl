<#-- get primary key -->
<#assign primaryKeyColumn = columns?filter(c -> c.isPrimaryKey())?first?default(columns[0])>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${basePackage}.mapper.${className}Mapper">

    <!-- 定义 resultMap -->
    <resultMap id="BaseResultMap" type="${basePackage}.model.${className}">
        <#list columns as column>
        <result column="${column.columnName}" property="${column.fieldName}" jdbcType="${column.jdbcType}" />
        </#list>
    </resultMap>

    <!-- 封装字段列表 -->
    <sql id="Base_Column_List">
        <#list columns as column>${column.columnName}<#if column_has_next>, </#if></#list>
    </sql>

    <!-- 查询全部 -->
    <select id="selectAll" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List" />
        FROM ${tableName}
    </select>

    <!-- 根据主键查询 -->
    <select id="selectByPrimaryKey" parameterType="${primaryKeyColumn.javaType}" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM ${tableName}
        WHERE ${primaryKeyColumn.columnName} = <#noparse>#{</#noparse>${primaryKeyColumn.fieldName}, jdbcType=${primaryKeyColumn.jdbcType}}
    </select>

    <!-- 根据对象条件查询 -->
    <select id="selectByModel" parameterType="${basePackage}.model.${className}" resultMap="BaseResultMap">
        SELECT <include refid="Base_Column_List" />
        FROM ${tableName}
        <where>
            <#list columns as column>
            <if test="${column.fieldName} != null <#if column.javaType == 'String'>and ${column.fieldName} != ''</#if>">
                AND ${column.columnName} = <#noparse>#{</#noparse>${column.fieldName}, jdbcType=${column.jdbcType}}
            </if>
            </#list>
        </where>
    </select>

    <!-- 插入 -->
    <insert id="insert" parameterType="${basePackage}.model.${className}" useGeneratedKeys="true" keyProperty="${primaryKeyColumn.fieldName}" keyColumn="${primaryKeyColumn.columnName}">
        INSERT INTO ${tableName} (
        <#list columns as column>
            ${column.columnName}<#if column_has_next>, </#if>
        </#list>
        )
        VALUES (
        <#list columns as column>
            <#noparse>#{</#noparse>${column.fieldName}, jdbcType=${column.jdbcType}}<#if column_has_next>, </#if>
        </#list>
        )
    </insert>

    <!-- 批量插入 -->
    <insert id="batchInsert" parameterType="java.util.List">
        INSERT INTO ${tableName} (
        <#list columns as column>
            ${column.columnName}<#if column?has_next>,</#if>
        </#list>
        )
        VALUES
        <foreach collection="list" item="item" separator="," open="(" close=")">
        <#list columns as column>
            <#noparse>#{</#noparse>item.${column.fieldName}}<#if column?has_next>,</#if>
        </#list>
        </foreach>
    </insert>

    <!-- 更新 -->
    <update id="updateByPrimaryKey" parameterType="${basePackage}.model.${className}">
        UPDATE ${tableName}
        SET
            <#list columns as column>
            <#if !column.isPrimaryKey()>${column.columnName} = <#noparse>#{</#noparse>${column.fieldName}, jdbcType=${column.jdbcType}}<#if column_has_next>, </#if></#if>
            </#list>
        WHERE ${primaryKeyColumn.columnName} = <#noparse>#{</#noparse>${primaryKeyColumn.fieldName}, jdbcType=${primaryKeyColumn.jdbcType}}
    </update>

    <!-- 按非空字段选择性更新 -->
    <update id="updateBySelective" parameterType="${basePackage}.model.${className}">
        UPDATE ${tableName}
        <set>
            <#list columns as column><#if !column.isPrimaryKey()>
            <if test="${column.fieldName} != null<#if column.javaType == 'String'> and ${column.fieldName} != ''</#if>">
                ${column.columnName} = <#noparse>#</#noparse>{${column.fieldName}, jdbcType=${column.jdbcType}},
            </if>
            </#if></#list>
        </set>
        WHERE ${primaryKeyColumn.columnName} = <#noparse>#{</#noparse>${primaryKeyColumn.fieldName}, jdbcType=${primaryKeyColumn.jdbcType}}
    </update>

    <!-- 删除 -->
    <delete id="deleteByPrimaryKey" parameterType="${primaryKeyColumn.javaType}">
        DELETE FROM ${tableName}
        WHERE ${primaryKeyColumn.columnName} = <#noparse>#{</#noparse>${primaryKeyColumn.fieldName}, jdbcType=${primaryKeyColumn.jdbcType}}
    </delete>

</mapper>
