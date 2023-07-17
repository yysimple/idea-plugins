package com.simple.idea.plugin.know;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

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

        if (editor != null) {
            // 获取选中的文本
            SelectionModel selectionModel = editor.getSelectionModel();
            String selectedText = selectionModel.getSelectedText();

            // 打印选中的文本
            if (selectedText != null) {
                System.out.println("Selected text: " + selectedText);
                // 这里的 project：对应的是后面内容将会在 窗体中展示；title：这个是窗体的标题
                Messages.showMessageDialog(project, selectedText, "枚举含义：", Messages.getInformationIcon());
            } else {
                System.out.println("No text selected.");
            }
        }
    }
}
