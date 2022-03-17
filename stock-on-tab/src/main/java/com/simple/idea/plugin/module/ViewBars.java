package com.simple.idea.plugin.module;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.JBSplitter;
import com.simple.idea.plugin.ui.ConsoleUI;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 这个是用来展示的主体内容
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:34
 **/
public class ViewBars extends SimpleToolWindowPanel {
    private Project project;
    private ConsoleUI consoleUI;

    public ViewBars(Project project) {
        super(false, true);
        this.project = project;
        // 实际构建数据逻辑在此
        consoleUI = new ConsoleUI();

        // 设置窗体侧边栏按钮
        DefaultActionGroup group = new DefaultActionGroup();
        group.add(new SettingBar(this));
        group.add(new RefreshBar(this));

        ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("bar", group, false);
        toolbar.setTargetComponent(this);
        setToolbar(toolbar.getComponent());

        // 添加
        JBSplitter splitter = new JBSplitter(false);
        splitter.setSplitterProportionKey("main.splitter.key");
        splitter.setFirstComponent(consoleUI.getPanel());
        splitter.setProportion(0.3f);
        setContent(splitter);
    }

    public Project getProject() {
        return project;
    }

    public ConsoleUI getConsoleUI() {
        return consoleUI;
    }
}
