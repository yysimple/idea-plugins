package com.simple.idea.plugin.mvc.infrastructure;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:40
 **/
public class ICONS {

    /**
     * 获取资源文件下面的图片信息
     * @param path
     * @return
     */
    private static Icon load(String path) {
        return IconLoader.getIcon(path, ICONS.class);
    }

    public static final Icon DDD = load("/icons/DDD.png");

    public static final Icon SPRING_BOOT = load("/icons/spring-boot.png");
}
