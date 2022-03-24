package com.simple.idea.plugin.mvc.domain.service;

import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.mvc.domain.model.vo.ProjectConfigVO;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:28
 **/
public interface IProjectGenerator {

    /**
     *
     * 这里只是一个触发点，供后面调用，具体实现交由子类去做
     * @param project
     * @param entryPath
     * @param projectConfig
     */
    void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);
}
