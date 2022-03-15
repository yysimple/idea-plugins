package com.simple.idea.plugin.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.simple.idea.plugin.infrastructure.DataSetting;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-15 17:04
 **/
public class GidConfig implements Configurable {
    private JPanel mainPanel;
    private JPanel settingPanel;
    private JLabel gidLabel;
    private JTextField gidTextField;

    private ConsoleUI consoleUI;

    public GidConfig(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    public JTextField getGidTextField() {
        return gidTextField;
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getDisplayName() {
        return "Stock";
    }

    @Override
    public @Nullable JComponent createComponent() {
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        List<String> gidList = DataSetting.getInstance().getGids();
        gidList.clear();
        String[] gids = gidTextField.getText().trim().split(",");
        for (String gid : gids) {
            gidList.add(gid.trim());
        }
        // 刷新数据
        consoleUI.addRows(gidList);
    }
}
