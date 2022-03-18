package com.simple.idea.plugin.domain.service;

import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.domain.model.vo.ProjectConfigVO;

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
     * @param project
     * @param entryPath
     * @param projectConfig
     */
    void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig);
}
