package com.simple.idea.plugin.mybatis.infrastructure.data;

import com.simple.idea.plugin.mybatis.domain.model.vo.ORMConfigVO;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 这个就是一个数据聚合模型，之后之后用来存放数据的
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:55
 **/
public class DataState {

    /**
     * orm相关的配置项
     */
    private ORMConfigVO ormConfigVO = new ORMConfigVO();

    /**
     * 一些配置项
     */
    private GenerateOptions options = new GenerateOptions();


    public ORMConfigVO getOrmConfigVO() {
        return ormConfigVO;
    }

    public void setOrmConfigVO(ORMConfigVO ormConfigVO) {
        this.ormConfigVO = ormConfigVO;
    }

    public GenerateOptions getOptions() {
        return options;
    }

    public void setOptions(GenerateOptions options) {
        this.options = options;
    }
}
