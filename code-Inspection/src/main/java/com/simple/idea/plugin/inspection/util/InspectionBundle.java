package com.simple.idea.plugin.inspection.util;

import com.intellij.AbstractBundle;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 获取自定义属性值
 *
 * @author: WuChengXing
 * @create: 2022-04-11 18:52
 **/
public class InspectionBundle extends AbstractBundle {

    @NonNls
    public static final String BUNDLE = "InspectionBundle";
    private static final AbstractBundle INSTANCE = new InspectionBundle();

    private InspectionBundle() {
        super(BUNDLE);
    }

    @NotNull
    public static String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, @NotNull Object... params) {
        return INSTANCE.getMessage(key, params);
    }
}
