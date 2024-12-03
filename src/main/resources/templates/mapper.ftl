<#assign primaryKeyColumn = columns?filter(c -> c.isPrimaryKey())?first?default(columns[0])>
package ${basePackage}.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import ${basePackage}.model.${className};

/**
* Mapper for ${tableName}
*
* @author: ${author}
*/
@Mapper
public interface ${className}Mapper {

    /**
    * 查询所有记录
    * @return 所有记录
    */
    List<${className}> selectAll();


    /**
    * 根据主键查询
    * @param id 主键ID
    * @return 对应的记录
    */
    ${className} selectByPrimaryKey(@Param("id") ${primaryKeyColumn.javaType} id);

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
    int batchInsert(@Param("list") List<${className}> records);


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
    * @param id 主键ID
    * @return 删除影响的行数
    */
    int deleteByPrimaryKey(@Param("id") ${primaryKeyColumn.javaType} id);

}
