package com.simple.idea.plugin.mybatis.infrastructure.po;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 传给 ftl 文件的数据
 *
 * @author: WuChengXing
 * @create: 2022-03-31 17:22
 **/
public class FreeMarkerData {

    private Model model;
    private Mapper mapper;
    private String author;
    private String projectName;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
