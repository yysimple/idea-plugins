package com.simple.idea.plugin.know.service.impl;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.simple.idea.plugin.know.data.CacheInit;
import com.simple.idea.plugin.know.data.DataSetting;
import com.simple.idea.plugin.know.service.EnumKnowService;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 实现
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-17 17:24
 **/
public class EnumKnowServiceImpl implements EnumKnowService {

    private final Project project;

    public EnumKnowServiceImpl(Project project) {
        this.project = project;
    }

    @Override
    public String getMessage(String keyword) {
        DataSetting instance = DataSetting.getInstance(null != project ? project : ProjectManager.getInstance().getDefaultProject());
        DataSetting.EnumKnowOptionsConfig enumKnowOptionsConfig = instance.getEnumKnowOptionsConfig();
        return CacheInit.search(keyword, enumKnowOptionsConfig.getReadMode());
    }
}
