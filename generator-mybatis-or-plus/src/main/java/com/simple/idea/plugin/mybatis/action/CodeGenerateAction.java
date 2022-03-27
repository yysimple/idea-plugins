package com.simple.idea.plugin.mybatis.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.mybatis.domain.service.IProjectGenerator;
import com.simple.idea.plugin.mybatis.domain.service.impl.ProjectGeneratorImpl;
import com.simple.idea.plugin.mybatis.ui.ORMSettingsUI;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:44
 **/
public class CodeGenerateAction extends AnAction {
    private IProjectGenerator projectGenerator = new ProjectGeneratorImpl();

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 这里是所有操作的入口，这里是注册到plugin.xml文件中，来触发全局操作
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        ShowSettingsUtil.getInstance().editConfigurable(project, new ORMSettingsUI(project, projectGenerator));
    }
}
