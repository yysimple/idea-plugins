package com.simple.idea.plugin.completion.infrastructure;

import com.intellij.psi.codeStyle.NameUtil;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-16 12:30
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
}
