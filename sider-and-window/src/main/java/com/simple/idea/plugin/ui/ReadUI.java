package com.simple.idea.plugin.ui;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-02-24 13:29
 **/
public class ReadUI {
    private JPanel mainPanel;
    private JTextPane textContent;

    public JComponent getComponent() {
        return mainPanel;
    }

    public JTextPane getTextContent() {
        return textContent;
    }
}
