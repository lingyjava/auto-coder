package ${packageName};

<#list importPackPath as path>
import ${path};
</#list>

/**
* @description: ${classDescription}
* @author: ${author}
* /
@Service
public class ${className} implements ${serviceName} {

    @Autowired
    private ${mapperName} ${mapperName?uncap_first};

    /**
    * 获取
    * /
    @Override
    public ${modelName} get(Long id) {
        return ${mapperName?uncap_first}.selectByPrimaryKey(id);
    }

    /**
    * 新增
    * /
    @Override
    public ${modelName} add(${modelName} model) {
        ${mapperName?uncap_first}.insert(model);
        return model;
    }

    /**
    * 更新
    * /
    @Override
    public int modify(${modelName} model) {
        return ${mapperName?uncap_first}.update(model);
    }

    /**
    * 删除
    * /
    @Override
    public int remove(Long id) {
        return ${mapperName?uncap_first}.delete(model);
    }

}