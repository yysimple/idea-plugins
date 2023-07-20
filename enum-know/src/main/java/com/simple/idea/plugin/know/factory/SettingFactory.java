package com.simple.idea.plugin.know.factory;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.simple.idea.plugin.know.data.CacheInit;
import com.simple.idea.plugin.know.data.DataSetting;
import com.simple.idea.plugin.know.ui.SettingUI;
import com.simple.idea.plugin.know.util.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 设置工厂
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-20 15:23
 **/
public class SettingFactory implements SearchableConfigurable {

    private SettingUI settingUI;

    @SuppressWarnings("FieldCanBeLocal")
    private final Project project;

    public SettingFactory(Project project) {
        this.project = project;
        settingUI = new SettingUI(project);
    }

    @Override
    public @NotNull
    String getId() {
        return "EnumKnow";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getDisplayName() {
        return "Enum Know Setting";
    }

    @Override
    public @Nullable
    JComponent createComponent() {
        return settingUI.getComponent();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        String url = "";
        DataSetting.EnumKnowFileConfig enumKnowFileConfig = DataSetting.getInstance(null != project ? project : ProjectManager.getInstance().getDefaultProject()).getEnumKnowFileConfig();
        // 如果缓存中存在(理论上一直会存在的，每次选择都会更新缓存)
        if (!Objects.isNull(enumKnowFileConfig) && !StringUtils.isEmpty(enumKnowFileConfig.getEnumKnowFilePath())) {
            url = enumKnowFileConfig.getEnumKnowFilePath();
        } else {
            url = settingUI.getUrlTextField().getText();
        }
        System.out.println("拿到的文件路径：" + url);
        // 设置缓存信息
        CacheInit.initCache(url);
    }
}
