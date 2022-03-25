package com.simple.idea.plugin.mvc.domain.service.impl;

import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.mvc.domain.model.vo.ProjectConfigVO;
import com.simple.idea.plugin.mvc.domain.service.AbstractProjectGenerator;

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
    protected void generateProjectMvc(Project project, String entryPath, ProjectConfigVO projectConfig) {
        // create controller
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".controller", entryPath, "package-info.java", "controller/package-info.ftl", projectConfig);

        // create common
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".common", entryPath, "package-info.java", "common/package-info.ftl", projectConfig);

        // create domain
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".domain", entryPath, "package-info.java", "domain/package-info.ftl", projectConfig);

        // create service
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".service", entryPath, "package-info.java", "service/package-info.ftl", projectConfig);

        // create service impl
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".service.impl", entryPath, "package-info.java", "service/impl/package-info.ftl", projectConfig);

        // create mapper
        writeFile(project, "src/main/java/" + projectConfig.get_package() + ".mapper", entryPath, "package-info.java", "mapper/package-info.ftl", projectConfig);
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
        writeFile(project, "src/main/java/" + projectConfig.get_package() + "/common", entryPath, "SimpleResponse.java", "common/SimpleResponse.ftl", projectConfig);
        writeFile(project, "src/main/java/" + projectConfig.get_package() + "/common", entryPath, "AirResponse.java", "common/AirResponse.ftl", projectConfig);
    }

    @Override
    protected void generateDemoClass(Project project, String entryPath, ProjectConfigVO projectConfig) {
        String baseJavaPath = "src/main/java/" + projectConfig.get_package();
        writeFile(project, baseJavaPath + "/controller", entryPath, "DemoController.java", "demo/DemoController.ftl", projectConfig);
        writeFile(project, baseJavaPath + "/domain", entryPath, "Demo.java", "demo/Demo.ftl", projectConfig);
        writeFile(project, baseJavaPath + "/service", entryPath, "DemoService.java", "demo/DemoService.ftl", projectConfig);
        writeFile(project, baseJavaPath + "/service/impl", entryPath, "DemoServiceImpl.java", "demo/impl/DemoServiceImpl.ftl", projectConfig);
        writeFile(project, baseJavaPath + "/mapper", entryPath, "DemoMapper.java", "demo/DemoMapper.ftl", projectConfig);
    }

    @Override
    protected void generateXml(Project project, String entryPath, ProjectConfigVO projectConfig) {
        writeFile(project, "src/main/resources/mapper", entryPath, "DemoMapper.xml", "xml.ftl", projectConfig);
    }
}
