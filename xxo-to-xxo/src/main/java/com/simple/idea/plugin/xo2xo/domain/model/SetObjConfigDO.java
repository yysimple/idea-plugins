package com.simple.idea.plugin.xo2xo.domain.model;

import java.util.List;
import java.util.Map;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-20 20:05
 **/
public class SetObjConfigDO {
    /**
     * 类的名称
     */
    private String clazzName;
    /**
     * 类路径名
     */
    private String qualifiedName;
    /**
     * 类属性名
     */
    private String clazzParamName;
    /**
     * param 集合，保证顺序性
     */
    private List<String> paramList;
    /**
     * key：param val：set方法
     */
    private Map<String, String> paramMtdMap;
    /**
     * 位移记录
     */
    private int repair;

    public SetObjConfigDO(String clazzName, String qualifiedName, String clazzParamName, List<String> paramList, Map<String, String> paramMtdMap, int repair) {
        this.clazzName = clazzName;
        this.qualifiedName = qualifiedName;
        this.clazzParamName = clazzParamName;
        this.paramList = paramList;
        this.paramMtdMap = paramMtdMap;
        this.repair = repair;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getClazzParamName() {
        return clazzParamName;
    }

    public void setClazzParamName(String clazzParamName) {
        this.clazzParamName = clazzParamName;
    }

    public List<String> getParamList() {
        return paramList;
    }

    public void setParamList(List<String> paramList) {
        this.paramList = paramList;
    }

    public Map<String, String> getParamMtdMap() {
        return paramMtdMap;
    }

    public void setParamMtdMap(Map<String, String> paramMtdMap) {
        this.paramMtdMap = paramMtdMap;
    }

    public int getRepair() {
        return repair;
    }

    public void setRepair(int repair) {
        this.repair = repair;
    }
}
