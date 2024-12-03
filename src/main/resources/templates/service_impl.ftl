package ${basePackage}.service.impl;

import ${basePackage}.mapper.${className}Mapper;
import ${basePackage}.model.${className};
import ${basePackage}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* ${tableName} Service 实现*
*
* @author: ${author}
*/
@Service
public class ${className}ServiceImpl implements ${className}Service {

    private final ${className}Mapper ${className?lower_case}Mapper;

    @Autowired
    public ${className}ServiceImpl(${className}Mapper ${className?lower_case}Mapper) {
        this.${className?lower_case}Mapper = ${className?lower_case}Mapper;
    }

    @Override
    public List<${className}> selectAll() {
        return ${className?lower_case}Mapper.selectAll();
    }

    @Override
    public ${className} selectByPrimaryKey(${columns?filter(c -> c.isPrimaryKey())?first.javaType} id) {
        return ${className?lower_case}Mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<${className}> selectByModel(${className} params) {
        return ${className?lower_case}Mapper.selectByModel(params);
    }

    @Override
    public int insert(${className} record) {
        return ${className?lower_case}Mapper.insert(record);
    }

    @Override
    public int batchInsert(List<${className}> records) {
        return ${className?lower_case}Mapper.batchInsert(records);
    }

    @Override
    public int updateByPrimaryKey(${className} record) {
        return ${className?lower_case}Mapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBySelective(${className} record) {
        return ${className?lower_case}Mapper.updateBySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(${columns?filter(c -> c.isPrimaryKey())?first.javaType} id) {
        return ${className?lower_case}Mapper.deleteByPrimaryKey(id);
    }
}
