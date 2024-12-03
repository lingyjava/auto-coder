package ${basePackage}.controller;

import ${basePackage}.model.${className};
import ${basePackage}.service.${className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* ${className} Controller
*
* @author: ${author}
*/
@RestController
@RequestMapping("/${className?lower_case}")
public class ${className}Controller {

    private final ${className}Service ${className?substring(0, 1)?lower_case + className?substring(1)}Service;

    @Autowired
    public ${className}Controller(${className}Service ${className?substring(0, 1)?lower_case + className?substring(1)}Service) {
        this.${className?substring(0, 1)?lower_case + className?substring(1)}Service = ${className?substring(0, 1)?lower_case + className?substring(1)}Service;
    }

    /**
    * 查询所有记录
    * @return 所有记录
    */
    @GetMapping
    public List<${className}> getAll() {
        return ${className?substring(0, 1)?lower_case + className?substring(1)}Service.selectAll();
    }

    /**
    * 根据主键查询记录
    * @param id 主键
    * @return 对应的记录
    */
    @GetMapping("/{id}")
    public ${className} getById(@PathVariable("${columns?filter(c -> c.isPrimaryKey())?first.columnName?lower_case}") ${columns?filter(c -> c.isPrimaryKey())?first.javaType} id) {
        return ${className?substring(0, 1)?lower_case + className?substring(1)}Service.selectByPrimaryKey(id);
    }

    /**
    * 新增记录
    * @param ${className?substring(0, 1)?lower_case + className?substring(1)} 新增的记录
    * @return 操作结果
    */
    @PostMapping
    public int add(@RequestBody ${className} ${className?substring(0, 1)?lower_case + className?substring(1)}) {
        return ${className?substring(0, 1)?lower_case + className?substring(1)}Service.insert(${className?substring(0, 1)?lower_case + className?substring(1)});
    }

    /**
    * 批量新增记录
    * @param records 批量记录
    * @return 操作结果
    */
    @PostMapping("/batch")
    public int batchAdd(@RequestBody List<${className}> records) {
        return ${className?substring(0, 1)?lower_case + className?substring(1)}Service.batchInsert(records);
    }

    /**
    * 更新记录
    * @param id 主键
    * @param ${className?substring(0, 1)?lower_case + className?substring(1)} 更新的记录
    * @return 操作结果
    */
    @PutMapping("/{id}")
    public int update(@PathVariable("${columns?filter(c -> c.isPrimaryKey())?first.columnName?lower_case}") ${columns?filter(c -> c.isPrimaryKey())?first.javaType} id, @RequestBody ${className} ${className?substring(0, 1)?lower_case + className?substring(1)}) {
        ${className?substring(0, 1)?lower_case + className?substring(1)}.setId(id);
        return ${className?substring(0, 1)?lower_case + className?substring(1)}Service.updateByPrimaryKey(${className?substring(0, 1)?lower_case + className?substring(1)});
    }

    /**
    * 按非空字段选择性更新记录
    * @param ${className?substring(0, 1)?lower_case + className?substring(1)} 更新的记录
    * @return 操作结果
    */
    @PatchMapping
    public int updateSelective(@RequestBody ${className} ${className?substring(0, 1)?lower_case + className?substring(1)}) {
        return ${className?substring(0, 1)?lower_case + className?substring(1)}Service.updateBySelective(${className?substring(0, 1)?lower_case + className?substring(1)});
    }

    /**
    * 根据主键删除记录
    * @param id 主键
    * @return 操作结果
    */
    @DeleteMapping("/{id}")
    public int delete(@PathVariable("${columns?filter(c -> c.isPrimaryKey())?first.columnName?lower_case}") ${columns?filter(c -> c.isPrimaryKey())?first.javaType} id) {
        return ${className?substring(0, 1)?lower_case + className?substring(1)}Service.deleteByPrimaryKey(id);
    }
}
