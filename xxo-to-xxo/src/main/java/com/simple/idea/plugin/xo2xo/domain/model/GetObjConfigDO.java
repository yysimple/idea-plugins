package com.simple.idea.plugin.xo2xo.domain.model;

import java.util.Map;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-20 20:05
 **/
public class GetObjConfigDO {
    /** 类路径名 */
    private String qualifiedName;
    /** 类名称 */
    private String clazzName;
    /** 属性名 */
    private String clazzParam;

    /** key：param val：get方法 */
    private Map<String, String> paramMtdMap;

    public GetObjConfigDO(String qualifiedName, String clazzName, String clazzParam, Map<String, String> paramMtdMap) {
        this.qualifiedName = qualifiedName;
        this.clazzName = clazzName;
        this.clazzParam = clazzParam;
        this.paramMtdMap = paramMtdMap;
    }

    public String getQualifiedName() {
        return qualifiedName;
    }

    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getClazzParam() {
        return clazzParam;
    }

    public void setClazzParam(String clazzParam) {
        this.clazzParam = clazzParam;
    }

    public Map<String, String> getParamMtdMap() {
        return paramMtdMap;
    }

    public void setParamMtdMap(Map<String, String> paramMtdMap) {
        this.paramMtdMap = paramMtdMap;
    }
}
