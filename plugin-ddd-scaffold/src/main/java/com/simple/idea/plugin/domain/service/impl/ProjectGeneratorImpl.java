package com.simple.idea.plugin.domain.service.impl;

import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.domain.model.vo.ProjectConfigVO;
import com.simple.idea.plugin.domain.service.AbstractProjectGenerator;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:30
 **/
public class ProjectGeneratorImpl extends AbstractProjectGenerator {

    /**
     * 在指定目录生成pom文件
     *
     * @param project
     * @param entryPath
     * @param projectConfig
     */
    @Override
    protected void generateProjectPOM(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "/", entryPath, "pom.xml", "pom.ftl", projectConfig);
    }

    /**
     * 这里是为每个文件夹生成对应的包信息，也即是对应的 package-info 信息
     *
     * @param project
     * @param entryPath
     * @param projectConfig
     */
    @Override
    protected void generateProjectDDD(Project project, String entryPath, ProjectConfigVO projectConfig) {
        // create application
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".application", entryPath, "package-info.java", "application/package-info.ftl", projectConfig);

        // create common
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".common", entryPath, "package-info.java", "common/package-info.ftl", projectConfig);

        // create domain
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".domain", entryPath, "package-info.java", "domain/package-info.ftl", projectConfig);

        // create infrastructure
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".infrastructure", entryPath, "package-info.java", "infrastructure/package-info.ftl", projectConfig);

        // create interfaces
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".interfaces", entryPath, "package-info.java", "interfaces/package-info.ftl", projectConfig);
    }

    /**
     * 生成Springboot的主启动类
     *
     * @param project
     * @param entryPath
     * @param projectConfig
     */
    @Override
    protected void generateApplication(Project project, String entryPath, ProjectConfigVO projectConfig) {
        String artifactId = projectConfig.get_artifactId();
        writeFile(project, "src/main/java/" + projectConfig.get_package(), entryPath, (artifactId.substring(0, 1).toUpperCase() + artifactId.substring(1)) + "Application.java", "application.ftl", projectConfig);
    }

    @Override
    protected void generateYml(Project project, String entryPath, ProjectConfigVO projectConfig) {

        writeFile(project, "src/main/resources/", entryPath, "application.yml", "yml.ftl", projectConfig);
    }

    @Override
    protected void generateCommon(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/java/" + projectConfig.get_package() + "/common", entryPath, "Constants.java", "common/Constants.ftl", projectConfig);
    }
}
