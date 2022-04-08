package com.simple.idea.plugin.mybatis.domain.model.vo;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: orm生成文件的基本信息
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:49
 **/
public class ORMConfigVO {

    /**
     * 数据库url
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String user;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目的根路径
     */
    private String classpath;

    /**
     *
     */
    private Integer transactionType;

    /**
     *
     */
    private String transactionName;

    /**
     *
     */
    private Integer isColumnNameDelimited;

    /**
     * 对应数据库里面的所有表名
     */
    private Set<String> tableNames = new LinkedHashSet<>();

    /**
     *
     */
    private String generatorType;

    /**
     * po模型对应的生成路径
     */
    private String poPath;

    /**
     * dao层的生成路径
     */
    private String daoPath;

    /**
     * service层的生成路径
     */
    private String servicePath;

    /**
     * service实现类生成路径
     */
    private String implPath;

    /**
     * controller生成类
     */
    private String controllerPath;

    /**
     * xml文件的生成路径
     */
    private String xmlPath;

    /**
     * 作者名称
     */
    private String author = "yysimple";

    /**
     * 下面这些是连接信息
     */
    private String host = "127.0.0.1";

    private String port = "3306";

    private String database;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public Integer getIsColumnNameDelimited() {
        return isColumnNameDelimited;
    }

    public void setIsColumnNameDelimited(Integer isColumnNameDelimited) {
        this.isColumnNameDelimited = isColumnNameDelimited;
    }

    public Set<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(Set<String> tableNames) {
        this.tableNames = tableNames;
    }

    public String getGeneratorType() {
        return generatorType;
    }

    public void setGeneratorType(String generatorType) {
        this.generatorType = generatorType;
    }

    public String getPoPath() {
        return poPath;
    }

    public void setPoPath(String poPath) {
        this.poPath = poPath;
    }

    public String getDaoPath() {
        return daoPath;
    }

    public void setDaoPath(String daoPath) {
        this.daoPath = daoPath;
    }

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getImplPath() {
        return implPath;
    }

    public void setImplPath(String implPath) {
        this.implPath = implPath;
    }

    public String getControllerPath() {
        return controllerPath;
    }

    public void setControllerPath(String controllerPath) {
        this.controllerPath = controllerPath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
