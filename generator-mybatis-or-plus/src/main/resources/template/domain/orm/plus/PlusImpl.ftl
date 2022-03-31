package ${package};

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
<#list imports as import>
import ${import};
</#list>

/**
* 项目: ${projectName}
*
* 功能描述: ${comment}
*
* @author: ${author}
* @create: ${.now?string('yyyy-MM-dd HH:mm:ss')}
**/
@Service
public class ${simpleName} extends ServiceImpl<${mapper.simpleName}, ${model.simpleName}}> implements ${service.simpleName} {

    @Resource
    private ${mapper.simpleName} ${mapper.varName}

<#list model.fields as field>
<#if field.id>
    /**
    * 通过ID查询单个${model.comment}
    *
    * @param id ID
    * @return {@link ${model.simpleName}}
    */
    public ${model.simpleName} find${model.simpleName}ById(${field.typeSimpleName} id) {
        return ${mapper.varName}.find${model.simpleName}ById(id);
    }

    /**
    * 新增${model.comment}
    *
    * @param ${model.varName} ${model.comment}
    */
    public void insert${model.simpleName}(${model.simpleName} ${model.varName}) {
        ${mapper.varName}.insert${model.simpleName}(${model.varName});
    }

    /**
    * 修改${model.comment}
    *
    * @param ${model.varName} ${model.comment}
    */
    public void update${model.simpleName}(${model.simpleName} ${model.varName}) {
        ${mapper.varName}.update${model.simpleName}(${model.varName});
    }

    /**
    * 通过ID删除单个${model.comment}
    *
    * @param id ID
    */
    public void delete${model.simpleName}ById(${field.typeSimpleName} id) {
        ${mapper.varName}.delete${model.simpleName}ById(id);
    }
</#if>
</#list>
}