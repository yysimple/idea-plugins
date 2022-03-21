package com.simple.idea.plugin.xo2xo.application;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-20 20:05
 **/
public interface IGenerateXxo2Xxo {
    /**
     * 代码生成的主流程
     *
     * @param project
     * @param dataContext
     * @param psiFile
     */
    void doGenerate(Project project, DataContext dataContext, PsiFile psiFile);
}
