package com.simple.idea.plugin.mybatis.domain.service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.simple.idea.plugin.mybatis.domain.model.vo.CodeGenContextVO;
import com.simple.idea.plugin.mybatis.infrastructure.data.GenerateOptions;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:11
 **/
public abstract class AbstractProjectGenerator extends GeneratorConfig implements IProjectGenerator {

    @Override
    public void generation(Project project, CodeGenContextVO codeGenContext, GenerateOptions options) {
        // 生成orm代码
        generateORM(project, codeGenContext, options);
    }

    /**
     * 具体生成逻辑交由子类去实现
     *
     * @param project
     * @param codeGenContext
     * @param options
     */
    protected abstract void generateORM(Project project, CodeGenContextVO codeGenContext, GenerateOptions options);

    /**
     * 这里就是实际的写文件，都是同样的模板
     *
     * @param project
     * @param packageName
     * @param name
     * @param ftl
     * @param dataModel
     */
    public void writeFile(Project project, String packageName, String name, String ftl, Object dataModel) {
        VirtualFile virtualFile = null;
        try {
            virtualFile = createPackageDir(packageName).createChildData(project, name);
            StringWriter stringWriter = new StringWriter();
            Template template = super.getTemplate(ftl);
            template.process(dataModel, stringWriter);
            virtualFile.setBinaryContent(stringWriter.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static VirtualFile createPackageDir(String packageName) {
        String path = FileUtil.toSystemIndependentName(StringUtil.replace(packageName, ".", "/"));
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }
}
