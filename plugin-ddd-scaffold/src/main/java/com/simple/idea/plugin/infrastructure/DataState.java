package com.simple.idea.plugin.infrastructure;

import com.simple.idea.plugin.domain.model.vo.ProjectConfigVO;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:38
 **/
public class DataState {
    /**
     * 这里的数据就是存储项目的一些初始化配置
     */
    private ProjectConfigVO projectConfigVO = new ProjectConfigVO();

    public ProjectConfigVO getProjectConfigVO() {
        return projectConfigVO;
    }

    public void setProjectConfigVO(ProjectConfigVO projectConfigVO) {
        this.projectConfigVO = projectConfigVO;
    }
}
