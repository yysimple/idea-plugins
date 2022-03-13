package com.simple.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-02-23 14:09
 **/
public class MyAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        String classPath = psiFile.getVirtualFile().getPath();
        // 这里的 project：对应的是后面内容将会在 窗体中展示；title：这个是窗体的标题
        Messages.showMessageDialog(project, "guide-idea-plugin-create-project-by-gradle: " + classPath, "Hi IDEA Plugin", Messages.getInformationIcon());
    }
}
