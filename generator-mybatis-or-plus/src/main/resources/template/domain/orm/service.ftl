package ${package};

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
public interface ${simpleName} {

}