package com.simple.idea.plugin.analyze.executor;

import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-14 17:21
 **/
public class DebugExecutor extends DefaultRunExecutor {
    public static final String DEBUG_ID = "Debug with CodeProbeLauncher";
    public static final String DEBUG_NAME = "Debug with CodeProbeLauncher";

    @NotNull
    @Override
    public String getId() {
        return DEBUG_ID;
    }

    @NotNull
    @Override
    public String getActionName() {
        return DEBUG_NAME;
    }

    @Override
    public String getDescription() {
        return DEBUG_ID;
    }

    @NotNull
    @Override
    public String getStartActionText() {
        return DEBUG_ID;
    }

    @NotNull
    @Override
    public String getToolWindowId() {
        return getId();
    }

    @NotNull
    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/images/debug-16.svg");
    }

    @NotNull
    @Override
    public Icon getToolWindowIcon() {
        return IconLoader.getIcon("/images/debug-13.svg");
    }

    @Override
    public Icon getDisabledIcon() {
        return null;
    }

    @Override
    public String getContextActionId() {
        return getId() + " context-action-does-not-exist";
    }

    @Override
    public String getHelpId() {
        return null;
    }
}
