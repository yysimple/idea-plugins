package ${_package + '.controller'};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 项目: ${_artifactId}
*
* 功能描述: demo对外接口
*
* @author: ${_author}
* @create: ${.now?string('yyyy-MM-dd HH:mm:ss')}
**/
@RestController
@RequestMapping("/${_artifactId}/demo")
public class DemoController {

}