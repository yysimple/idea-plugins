package com.simple.idea.plugin.mybatis.infrastructure.data;

import com.simple.idea.plugin.mybatis.domain.model.vo.ORMConfigVO;
import com.simple.idea.plugin.mybatis.infrastructure.utils.Constants;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 这个就是一个数据聚合模型，之后之后用来存放数据的
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:55
 **/
public class DataState {

    /**
     * orm相关的配置项
     */
    private ORMConfigVO ormConfigVO = new ORMConfigVO();

    /**
     * 一些配置项
     */
    private GenerateOptions options = new GenerateOptions();

    /**
     * 是否生成plus模板
     */
    private String isPlus = Constants.IS_PLUS;

    /**
     * 是否生成前置目录，比如生成类 User，并未其生成目录 domain/pojo/entity等
     */
    private String isCreateDir;

    /**
     * 是否生成 service 相关类
     */
    private String isCreateService;

    /**
     * 是否生成Controller 相关类
     */
    private String isCreateController;

    public ORMConfigVO getOrmConfigVO() {
        return ormConfigVO;
    }

    public void setOrmConfigVO(ORMConfigVO ormConfigVO) {
        this.ormConfigVO = ormConfigVO;
    }

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
