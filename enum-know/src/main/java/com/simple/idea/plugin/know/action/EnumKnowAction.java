package com.simple.idea.plugin.know.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.simple.idea.plugin.know.service.EnumKnowService;
import com.simple.idea.plugin.know.service.impl.EnumKnowServiceImpl;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 项目: idea-plugins
 *
 * 功能描述:
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-17 13:41
 **/
public class EnumKnowAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 获取当前的项目和编辑器
        Project project = e.getProject();
        Editor editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);

        Icon tureIcon = IconLoader.getIcon("/icons/精准匹配-32.png");
        Icon errorIcon = IconLoader.getIcon("/icons/警告-32.png");

        if (editor != null) {
            // 获取选中的文本
            SelectionModel selectionModel = editor.getSelectionModel();
            String selectedText = selectionModel.getSelectedText();
            EnumKnowService enumKnowService = new EnumKnowServiceImpl(project);
            // 打印选中的文本
            if (selectedText != null) {
                // 这里的 project：对应的是后面内容将会在 窗体中展示；title：这个是窗体的标题
                Messages.showMessageDialog(project, enumKnowService.getMessage(selectedText), "Enum Know：", tureIcon);
            } else {
                Messages.showMessageDialog(project, "Please you select text", "请选择文本：", errorIcon);
            }
        }
    }
}
