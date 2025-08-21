package ${packageName};

<#list importPackPath as path>
import ${path};
</#list>

/**
* @description: ${classDescription}
* @author: ${author}
* /
public interface ${className} {

    /**
    * 通过主键查询
    * /
    ${modelName} selectByPrimaryKey(Long id);

    /**
    * 通过Model查询
    * /
    List<${modelName}> selectByModel(${modelName} model);

    /**
    * 新增
    * /
    int insert(${modelName} model);

    /**
    * 新增多条
    * /
    int insertList(List<${modelName}> list);

    /**
    * 更新
    * /
    int update(${modelName} model);

    /**
    * 选择更新
    * /
    int updateBySelective(${modelName} model);

    /**
    * 删除
    * /
    int delete(Long id);

}