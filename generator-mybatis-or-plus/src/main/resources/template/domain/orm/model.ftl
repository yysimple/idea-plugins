package ${package};

import lombok.Data;
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
public class ${simpleName} {
<#list fields as field>
    /**
     * ${field.comment}
     */
    private ${field.typeSimpleName} ${field.name};
</#list>
}