package ${_package + '.service.impl'};

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${_package}.domain.Demo;
import ${_package}.mapper.DemoMapper;
import ${_package}.service.DemoService;
import org.springframework.stereotype.Service;

/**
* 项目: ${_artifactId}
*
* 功能描述: 业务实现
*
* @author: ${_author}
* @create: ${.now?string('yyyy-MM-dd HH:mm:ss')}
**/
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, Demo> implements DemoService {

}