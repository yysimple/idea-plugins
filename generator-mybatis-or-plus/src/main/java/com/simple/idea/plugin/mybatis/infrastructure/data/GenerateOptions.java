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

    private String isCreateDir;
    private String isCreateService;
    private String isCreateController;

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
}
