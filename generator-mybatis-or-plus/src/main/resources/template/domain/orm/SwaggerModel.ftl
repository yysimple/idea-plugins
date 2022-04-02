package ${package};

import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Data
@ApiModel(value="${simpleName}对象", description="${simpleName}基本信息表")
public class ${simpleName} {
<#list fields as field>
    /**
    * ${field.comment}
    */
    @ApiModelProperty(value = "${field.comment}")
    private ${field.typeSimpleName} ${field.name};
</#list>
}