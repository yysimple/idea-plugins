package ${_package + '.domain'};

import lombok.Data;

/**
* 项目: ${_artifactId}
*
* 功能描述: 实体
*
* @author: ${_author}
* @create: ${.now?string('yyyy-MM-dd HH:mm:ss')}
**/
@Data
public class Demo {
    private Long id;
    private String demoDesc;
}