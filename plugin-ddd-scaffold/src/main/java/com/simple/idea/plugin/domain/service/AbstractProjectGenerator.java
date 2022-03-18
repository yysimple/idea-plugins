package com.simple.idea.plugin.domain.service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.simple.idea.plugin.domain.model.vo.ProjectConfigVO;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:29
 **/
public abstract class AbstractProjectGenerator extends FreemarkerConfiguration implements IProjectGenerator {

    /**
     * 定义整个生成流程，具体的生成流程继续传递给子类实现
     * @param project
     * @param entryPath
     * @param projectConfig
     */
    @Override
    public void doGenerator(Project project, String entryPath, ProjectConfigVO projectConfig) {

        // 1.创建工程主POM文件
        generateProjectPOM(project, entryPath, projectConfig);

        // 2.创建四层架构
        generateProjectDDD(project, entryPath, projectConfig);

        // 3.创建 Application
        generateApplication(project, entryPath, projectConfig);

        // 4. 创建 Yml
        generateYml(project, entryPath, projectConfig);

        // 5. 创建 Common
        generateCommon(project, entryPath, projectConfig);
    }


    protected abstract void generateProjectPOM(Project project, String entryPath, ProjectConfigVO projectConfig);

    protected abstract void generateProjectDDD(Project project, String entryPath, ProjectConfigVO projectConfig);

    protected abstract void generateApplication(Project project, String entryPath, ProjectConfigVO projectConfig);

    protected abstract void generateYml(Project project, String entryPath, ProjectConfigVO projectConfig);

    protected abstract void generateCommon(Project project, String entryPath, ProjectConfigVO projectConfig);

    public void writeFile(Project project, String packageName, String entryPath, String name, String ftl, Object dataModel) {
        VirtualFile virtualFile = null;
        try {
            virtualFile = createPackageDir(packageName, entryPath).createChildData(project, name);
            StringWriter stringWriter = new StringWriter();
            Template template = super.getTemplate(ftl);
            // 会将上面ftl文件对应的内容转换成对应的字符串信息
            template.process(dataModel, stringWriter);
            // 然后这里交由Idea的api去完成目录的创建；其实也是通过文件的输出流完成
            virtualFile.setBinaryContent(stringWriter.toString().getBytes("UTF-8"));
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static VirtualFile createPackageDir(String packageName, String entryPath) {
        String path = FileUtil.toSystemIndependentName(entryPath + "/" + StringUtil.replace(packageName, ".", "/"));
        // 这里会去创建实际的文件
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }
}
