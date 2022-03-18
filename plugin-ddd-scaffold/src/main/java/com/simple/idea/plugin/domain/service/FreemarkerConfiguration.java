package com.simple.idea.plugin.domain.service;

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

    public FreemarkerConfiguration() {
        this("/template");
    }

    private FreemarkerConfiguration(String basePackagePath) {
        super(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        setDefaultEncoding("UTF-8");
        setClassForTemplateLoading(getClass(), basePackagePath);
    }

    @Override
    public Template getTemplate(String ftl) throws IOException {
        return this.getTemplate(ftl, "UTF-8");
    }
}
