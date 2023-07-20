package com.simple.idea.plugin.know.util;

import java.util.Objects;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 字符串工具类
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-20 17:18
 **/
public class StringUtils {

    public static boolean isEmpty(String str) {
        return Objects.isNull(str) || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
