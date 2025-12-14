package ${packageName};

<#list importPackPath as path>
import ${path};
</#list>

/**
* ${classDescription}
* @author: ${author}
* /
public interface ${className} {

    /**
    * 获取
    * /
    ${modelName} get(Long id);

    /**
    * 新增
    * /
    ${modelName} add(${modelName} model);

    /**
    * 更新
    * /
    int modify(${modelName} model);

    /**
    * 删除
    * /
    int remove(Long id);

}