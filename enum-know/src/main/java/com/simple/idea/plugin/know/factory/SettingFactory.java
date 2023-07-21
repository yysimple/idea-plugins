package com.simple.idea.plugin.know.factory;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.simple.idea.plugin.know.data.CacheInit;
import com.simple.idea.plugin.know.data.DataSetting;
import com.simple.idea.plugin.know.data.EnumKnowConstants;
import com.simple.idea.plugin.know.ui.SettingUI;
import com.simple.idea.plugin.know.util.StringUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
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
        // 处理文件
        dealFilePath();
        // 处理配置
        dealOptions();
        // 处理db
        dealDb();
    }

    private void dealFilePath() {
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

    private void dealOptions() {
        DataSetting.EnumKnowOptionsConfig enumKnowOptionsConfig = DataSetting.getInstance(null != project ? project : ProjectManager.getInstance().getDefaultProject()).getEnumKnowOptionsConfig();
        if (settingUI.getFileRead().isSelected()) {
            enumKnowOptionsConfig.setReadMode(EnumKnowConstants.READ_FILE);
        } else if (settingUI.getDbRead().isSelected()) {
            enumKnowOptionsConfig.setReadMode(EnumKnowConstants.READ_DB);
        } else if (settingUI.getAllRead().isSelected()) {
            enumKnowOptionsConfig.setReadMode(EnumKnowConstants.READ_ALL);
        } else {
            enumKnowOptionsConfig.setReadMode("");
        }
    }

    private void dealDb() {
        DataSetting.EnumKnowDataSourceConfig enumKnowDataSourceConfig = buildDbData();
        // 设置缓存信息
        CacheInit.initCache(enumKnowDataSourceConfig);
    }

    public DataSetting.EnumKnowDataSourceConfig buildDbData() {
        DataSetting.EnumKnowDataSourceConfig enumKnowDataSourceConfig = DataSetting.getInstance(null != project ? project : ProjectManager.getInstance().getDefaultProject()).getEnumKnowDataSourceConfig();
        enumKnowDataSourceConfig.setHost(settingUI.getHostTextField().getText());
        enumKnowDataSourceConfig.setPort(settingUI.getPortTextField().getText());
        enumKnowDataSourceConfig.setUsername(settingUI.getUsernameTextField().getText());
        enumKnowDataSourceConfig.setPassword(settingUI.getPasswordTextField().getText());
        enumKnowDataSourceConfig.setDatabase(settingUI.getDatabaseTextField().getText());
        enumKnowDataSourceConfig.setTableName(settingUI.getTableTextField().getText());
        enumKnowDataSourceConfig.setColumn(settingUI.getColumnTextField().getText());
        return enumKnowDataSourceConfig;
    }
}
