package ${_package}.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${_package}.domain.Demo;
import org.apache.ibatis.annotations.Mapper;

/**
* 项目: ${_artifactId}
*
* 功能描述:
*
* @author: ${_author}
* @create: ${.now?string('yyyy-MM-dd HH:mm:ss')}
**/
@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

}