package com.simple.idea.plugin.probe.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.extensions.PluginId;

import java.io.File;
import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-13 19:50
 **/
public class PluginUtil {
    private static final IdeaPluginDescriptor IDEA_PLUGIN_DESCRIPTOR;

    static {
        PluginId pluginId = PluginId.getId("com.simple.bytecode-sql-probe");
        IDEA_PLUGIN_DESCRIPTOR = PluginManagerCore.getPlugin(pluginId);
    }

    /**
     * 获取核心Jar路径
     *
     * @return String
     */
    public static String getAgentCoreJarPath() {
        return getJarPathByStartWith("SqlProbeAgent");
    }

    /**
     * 根据jar包的前缀名称获路径
     *
     * @param startWith 前缀名称
     * @return String
     */
    private static String getJarPathByStartWith(String startWith) {
        final String quotes = "\"";
        List<File> files = FileUtil.loopFiles(IDEA_PLUGIN_DESCRIPTOR.getPath());
        for (File file : files) {
            String name = file.getName();
            if (name.startsWith(startWith)) {
                String pathStr = FileUtil.getCanonicalPath(file);
                if (StrUtil.contains(pathStr, StrUtil.SPACE)) {
                    return StrUtil.builder().append(quotes).append(pathStr).append(quotes).toString();
                }
                return pathStr;
            }
        }
        return StrUtil.EMPTY;
    }
}
