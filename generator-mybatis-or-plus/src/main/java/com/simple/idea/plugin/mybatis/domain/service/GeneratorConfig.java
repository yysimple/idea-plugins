package com.simple.idea.plugin.mybatis.domain.service;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:11
 **/
public class GeneratorConfig {

    private static String ENCODING = "UTF-8";

    /**
     * 获取Freemarker的基础路径
     */
    private static FreemarkerConfiguration freemarker = new FreemarkerConfiguration("/template");

    /**
     * 获取模版
     *
     * @param ftl
     * @return
     * @throws IOException
     */
    protected Template getTemplate(String ftl) throws IOException {
        return freemarker.getTemplate(ftl, ENCODING);
    }

    static class FreemarkerConfiguration extends Configuration {
        public FreemarkerConfiguration(String basePackagePath) {
            super(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            setDefaultEncoding(ENCODING);
            setClassForTemplateLoading(getClass(), basePackagePath);
        }
    }
}
