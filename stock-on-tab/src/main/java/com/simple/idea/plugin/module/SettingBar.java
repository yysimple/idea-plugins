package com.simple.idea.plugin.module;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.util.IconLoader;
import com.simple.idea.plugin.ui.GidConfig;
import org.jetbrains.annotations.NotNull;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:34
 **/
public class SettingBar extends DumbAwareAction {
    private final ViewBars panel;

    public SettingBar(ViewBars panel) {
        super("配置股票", "Click to setting", IconLoader.getIcon("/icons/config.svg"));
        this.panel = panel;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ShowSettingsUtil.getInstance().editConfigurable(panel.getProject(), new GidConfig(panel.getConsoleUI()));
    }
}
