package com.simple.idea.plugin.know.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.simple.idea.plugin.know.data.DataSetting;

import javax.swing.*;
import java.io.File;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-02-24 13:08
 **/
public class SettingUI {

    private JPanel mainPanel;
    private JPanel settingPanel;
    private JLabel urlLabel;
    private JTextField urlTextField;
    private JButton urlButton;

    public SettingUI(Project project) {
        // 初始化操作，默认值加入
        DataSetting.EnumKnowFileConfig enumKnowFileConfig = DataSetting.getInstance(project).getState().getEnumKnowFileConfig();
        urlTextField.setText(enumKnowFileConfig.getEnumKnowFilePath());
        // 给按钮添加一个选择文件的事件
        urlButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.showOpenDialog(settingPanel);
            // 这里是上传一个文件路径
            File file = fileChooser.getSelectedFile();
            // 设置到组件属性中，之后通过这个属性拿到对应的文件流
            urlTextField.setText(file.getPath());
            enumKnowFileConfig.setEnumKnowFilePath(file.getPath());
        });
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    public JTextField getUrlTextField() {
        return urlTextField;
    }
}
