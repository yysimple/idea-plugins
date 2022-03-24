package com.simple.idea.plugin.mvc.domain.service;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:29
 **/
public class FreemarkerConfiguration extends Configuration {

    /**
     * 这里是去初始化我们自己设置的模板应该从哪里去读取
     */
    public FreemarkerConfiguration() {
        this("/template");
    }

    private FreemarkerConfiguration(String basePackagePath) {
        super(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        setDefaultEncoding("UTF-8");
        setClassForTemplateLoading(getClass(), basePackagePath);
    }

    /**
     * 通过配置模版文件名称拿到对应的模版信息
     * @param ftl
     * @return
     * @throws IOException
     */
    @Override
    public Template getTemplate(String ftl) throws IOException {
        return this.getTemplate(ftl, "UTF-8");
    }
}
