package com.simple.idea.plugin.xo2xo.ui;

import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.xo2xo.infrastructure.DataSetting;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 配置设置的UI界面
 *
 * @author: WuChengXing
 * @create: 2022-03-20 19:44
 **/
public class ConfigSettingUI {
    private JPanel main;
    private JRadioButton showRadioButton;
    private JRadioButton hideButton;

    public ConfigSettingUI(Project project) {
        // 添加按钮组
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(showRadioButton);
        buttonGroup.add(hideButton);
        DataSetting.DataState state = DataSetting.getInstance(project).getState();

        if ("hide".equals(state.getConfigRadio())) {
            hideButton.setSelected(true);
        } else {
            showRadioButton.setSelected(true);
        }
    }

    public JComponent getComponent() {
        return main;
    }

    /**
     *
     * @return
     */
    public String getRadioVal() {
        return showRadioButton.isSelected() ? "show" : "hide";
    }

}
