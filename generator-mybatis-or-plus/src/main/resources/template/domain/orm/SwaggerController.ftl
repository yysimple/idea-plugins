package ${package};

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;
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
@RestController
@RequestMapping("/${model.varName}")
@Api(tags = "${model.simpleName} 接口")
public class ${simpleName} {

    @Resource
    private ${service.simpleName} ${service.varName};

<#list model.fields as field>
<#if field.id>
    /**
    * 通过ID查询单个${model.comment}
    *
    * @param id ID
    * @return {@link ${model.simpleName}}
    */
    @ApiOperation(value = "通过Id查询${model.simpleName}信息")
    @GetMapping("/find${model.simpleName}ById")
    public ${response.simpleName}<${model.simpleName}> find${model.simpleName}ById(@RequestParam("id") ${field.typeSimpleName} id) {
        return new ${response.simpleName}<>(${service.varName}.find${model.simpleName}ById(id));
    }

    /**
    * 新增${model.comment}
    *
    * @param ${model.varName} ${model.comment}
    */
    @PostMapping("/insert${model.simpleName}")
    public ${response.simpleName}<String> insert${model.simpleName}(@RequestBody ${model.simpleName} ${model.varName}) {
        ${service.varName}.insert${model.simpleName}(${model.varName});
        return new ${response.simpleName}<>("insert success");
    }

    /**
    * 修改${model.comment}
    *
    * @param ${model.varName} ${model.comment}
    */
    @PostMapping("/update${model.simpleName}")
    public ${response.simpleName}<String> update${model.simpleName}(@RequestBody ${model.simpleName} ${model.varName}) {
    ${service.varName}.update${model.simpleName}(${model.varName});
        return new ${response.simpleName}<>("update success");
    }

    /**
    * 通过ID删除单个${model.comment}
    *
    * @param id ID
    */
    @PostMapping("/delete${model.simpleName}ById")
    public ${response.simpleName}<String> delete${model.simpleName}ById(@RequestParam("id") ${field.typeSimpleName} id) {
    ${service.varName}.delete${model.simpleName}ById(id);
        return new ${response.simpleName}<>("delete success");
    }
</#if>
</#list>
}