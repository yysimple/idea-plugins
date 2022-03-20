package com.simple.idea.plugin.orm.infrastructure.data;

import com.simple.idea.plugin.orm.domain.model.vo.ORMConfigVO;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 这个就是一个数据聚合模型，之后之后用来存放数据的
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:55
 **/
public class DataState {
    private ORMConfigVO ormConfigVO = new ORMConfigVO();

    public ORMConfigVO getOrmConfigVO() {
        return ormConfigVO;
    }

    public void setOrmConfigVO(ORMConfigVO ormConfigVO) {
        this.ormConfigVO = ormConfigVO;
    }
}
