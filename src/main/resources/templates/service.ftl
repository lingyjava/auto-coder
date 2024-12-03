<#assign primaryKeyColumn = columns?filter(c -> c.isPrimaryKey())?first?default(columns[0])>
package ${basePackage}.service;

import ${basePackage}.model.${className};
import java.util.List;

/**
* ${tableName} Service 接口
*
* @author: ${author}
*/
public interface ${className}Service {

    /**
    * 查询所有记录
    * @return 所有记录
    */
    List<${className}> selectAll();

    /**
    * 根据主键查询记录
    * @param id 主键
    * @return 对应的记录
    */
    ${className} selectByPrimaryKey(${primaryKeyColumn.javaType} id);

    /**
    * 根据条件查询所有记录
    * @param params 查询条件
    * @return 符合条件的记录列表
    */
    List<${className}> selectByModel(${className} params);

    /**
    * 插入一条记录
    * @param record 待插入的记录
    * @return 插入影响的行数
    */
    int insert(${className} record);

    /**
    * 批量插入
    * @param records 待插入的记录列表
    * @return 插入影响的行数
    */
    int batchInsert(List<${className}> records);

    /**
    * 更新记录（根据主键）
    * @param record 待更新的记录
    * @return 更新影响的行数
    */
    int updateByPrimaryKey(${className} record);

    /**
    * 按非空字段选择性更新（根据主键）
    * @param record 待更新的记录
    * @return 更新影响的行数
    */
    int updateBySelective(${className} record);

    /**
    * 根据主键删除
    * @param id 主键
    * @return 删除影响的行数
    */
    int deleteByPrimaryKey(${primaryKeyColumn.javaType} id);

}
