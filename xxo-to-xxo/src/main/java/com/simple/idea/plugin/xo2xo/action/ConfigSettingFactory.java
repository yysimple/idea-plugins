package com.simple.idea.plugin.xo2xo.action;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.xo2xo.infrastructure.DataSetting;
import com.simple.idea.plugin.xo2xo.ui.ConfigSettingUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-20 20:06
 **/
public class ConfigSettingFactory implements SearchableConfigurable {

    private ConfigSettingUI configSettingUI;

    @SuppressWarnings("FieldCanBeLocal")
    private final Project project;

    public ConfigSettingFactory(Project project) {
        this.project = project;
        configSettingUI = new ConfigSettingUI(project);
    }

    @Override
    public @NotNull String getId() {
        return "xxo2xxo";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Xxo2Xxo 是否弹窗提醒映射关系配置";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return configSettingUI.getComponent();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    /**
     * 这里很简单，这里只是简答的一个设置的地方；就是配置项 File -> Settings -> tools
     *
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        DataSetting.DataState state = DataSetting.getInstance(project).getState();
        assert state != null;
        // 设置的时候，将此次状态记录到DataState中
        state.setConfigRadio(configSettingUI.getRadioVal());
    }
}
