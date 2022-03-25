package com.simple.idea.plugin.mvc.ui;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 纯UI界面，用来展示和获取数据
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:52
 **/
public class ProjectConfigUI {
    private JPanel mainPanel;
    private JTextField groupIdField;
    private JTextField artifactIdField;
    private JTextField versionField;
    private JTextField packageField;
    private JTextField authorField;

    public JComponent getComponent(){
        return mainPanel;
    }

    public JTextField getGroupIdField() {
        return groupIdField;
    }

    public JTextField getArtifactIdField() {
        return artifactIdField;
    }

    public JTextField getVersionField() {
        return versionField;
    }

    public JTextField getPackageField() {
        return packageField;
    }

    public JTextField getAuthorField() {
        return authorField;
    }
}
