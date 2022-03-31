package ${package};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface ${simpleName} extends BaseMapper<${model.simpleName}> {

<#list model.fields as field>
<#if field.id>
    /**
    * 通过ID查询单个${model.comment}
    *
    * @param id ID
    * @return {@link ${model.simpleName}}
    */
    ${model.simpleName} find${model.simpleName}ById(${field.typeSimpleName} id);

    /**
    * 新增${model.comment}
    *
    * @param ${model.varName} ${model.comment}
    */
    void insert${model.simpleName}(${model.simpleName} ${model.varName});

    /**
    * 修改${model.comment}
    *
    * @param ${model.varName} ${model.comment}
    */
    void update${model.simpleName}(${model.simpleName} ${model.varName});

    /**
    * 通过ID删除单个${model.comment}
    *
    * @param id ID
    */
    void delete${model.simpleName}ById(${field.typeSimpleName} id);
</#if>
</#list>

}