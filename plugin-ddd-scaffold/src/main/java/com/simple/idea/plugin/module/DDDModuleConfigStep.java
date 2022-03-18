package com.simple.idea.plugin.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.options.ConfigurationException;
import com.simple.idea.plugin.domain.model.vo.ProjectConfigVO;
import com.simple.idea.plugin.infrastructure.DataSetting;
import com.simple.idea.plugin.ui.ProjectConfigUI;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 初始化时的每一个界面步骤的配置，不过这里在新版idea中会稍微有点不同；
 * 官方文档：https://plugins.jetbrains.com/docs/intellij/adding-new-steps.html#checking-ui-appearance
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:42
 **/
public class DDDModuleConfigStep extends ModuleWizardStep {

    private ProjectConfigUI projectConfigUI;

    public DDDModuleConfigStep(ProjectConfigUI projectConfigUI) {
        this.projectConfigUI = projectConfigUI;
    }

    @Override
    public JComponent getComponent() {
        return projectConfigUI.getComponent();
    }

    @Override
    public void updateDataModel() {

    }

    /**
     * 这里是我们点击next的时候的模块初始化的那个界面
     *
     * @return
     * @throws ConfigurationException
     */
    @Override
    public boolean validate() throws ConfigurationException {
        // 获取配置信息，写入到 DataSetting
        ProjectConfigVO projectConfig = DataSetting.getInstance().getProjectConfig();
        projectConfig.set_groupId(projectConfigUI.getGroupIdField().getText());
        projectConfig.set_artifactId(projectConfigUI.getArtifactIdField().getText());
        projectConfig.set_version(projectConfigUI.getVersionField().getText());
        projectConfig.set_package(projectConfigUI.getPackageField().getText());

        return super.validate();
    }
}
