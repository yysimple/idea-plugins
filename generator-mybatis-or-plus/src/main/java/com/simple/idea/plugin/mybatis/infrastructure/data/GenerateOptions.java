package com.simple.idea.plugin.mybatis.infrastructure.data;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 生成文件的一些选项
 *
 * @author: WuChengXing
 * @create: 2022-03-27 13:53
 **/
public class GenerateOptions {

    /**
     * 是否生成plus模板
     */
    private String isPlus;

    /**
     * 是否生成前置目录，相当于我们生成一个 User类的话，对于mvc中的思想，那就会为其生成一个对应的 domain/entity/pojo等类似的包名
     */
    private String isCreateDir;

    /**
     * 是否生成指定的 Service / ServiceImpl
     */
    private String isCreateService;

    /**
     * 是否生成指定的 Controller
     */
    private String isCreateController;

    /**
     * 是否生成Swagger
     */
    private String isCreateSwagger;

    public String getIsPlus() {
        return isPlus;
    }

    public void setIsPlus(String isPlus) {
        this.isPlus = isPlus;
    }

    public String getIsCreateDir() {
        return isCreateDir;
    }

    public void setIsCreateDir(String isCreateDir) {
        this.isCreateDir = isCreateDir;
    }

    public String getIsCreateService() {
        return isCreateService;
    }

    public void setIsCreateService(String isCreateService) {
        this.isCreateService = isCreateService;
    }

    public String getIsCreateController() {
        return isCreateController;
    }

    public void setIsCreateController(String isCreateController) {
        this.isCreateController = isCreateController;
    }

    public String getIsCreateSwagger() {
        return isCreateSwagger;
    }

    public void setIsCreateSwagger(String isCreateSwagger) {
        this.isCreateSwagger = isCreateSwagger;
    }
}
