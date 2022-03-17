package com.simple.idea.plugin.module;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.util.IconLoader;
import com.simple.idea.plugin.infrastructure.DataSetting;
import org.jetbrains.annotations.NotNull;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:34
 **/
public class RefreshBar extends DumbAwareAction {
    private ViewBars panel;

    public RefreshBar(ViewBars panel) {
        super("刷新指数", "Click to refresh", IconLoader.getIcon("/icons/refresh.svg"));
        this.panel = panel;
    }

    /**
     * 执行刷新动作
     *
     * @param e
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 点击的时候，这里是会去刷新数据的
        panel.getConsoleUI().addRows(DataSetting.getInstance().getGids());
    }
}
