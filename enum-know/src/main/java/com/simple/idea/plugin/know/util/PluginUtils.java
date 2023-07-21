package com.simple.idea.plugin.know.util;

import com.intellij.psi.codeStyle.NameUtil;

import java.lang.reflect.Field;

/**
 * 项目: idea-plugins
 *
 * 功能描述:
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-20 17:14
 **/
public class PluginUtils {

    private static final String EMPTY_STRING = "";
    private static final char UNDERSCORE = '_';

    private PluginUtils() {
    }

    public static String getLastWord(String name) {
        int length = name.length();
        if (length == 0 || isUnderscore(name.charAt(length - 1))) {
            return EMPTY_STRING;
        }

        String[] words = NameUtil.splitNameIntoWords(name);
        if (words.length > 0) {
            return words[words.length - 1];
        }

        return EMPTY_STRING;
    }


    public static boolean isUnderscore(char c) {
        return c == '_';
    }

    public static boolean isCompositeName(String name) {
        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c) || UNDERSCORE == c) {
                return true;
            }
        }
        return false;
    }

    public static boolean allFieldIsNULL(Object object) {
        if (null == object) {
            return true;
        }
        try {
            // 挨个获取对象属性值
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 如果有一个属性值不为null，且值不是空字符串，就返回false
                if (f.get(object) != null && StringUtils.isNotEmpty(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean allFieldNonNULL(Object object) {
        if (null == object) {
            return false;
        }
        try {
            // 挨个获取对象属性值
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                // 如果有一个属性值不为null，且值不是空字符串，就返回false
                if (f.get(object) == null || StringUtils.isEmpty(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
