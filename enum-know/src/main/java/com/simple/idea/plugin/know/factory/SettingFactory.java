package com.simple.idea.plugin.know.factory;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.simple.idea.plugin.know.ui.SettingUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 设置工厂
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-20 15:23
 **/
public class SettingFactory implements SearchableConfigurable {

    private SettingUI settingUI = new SettingUI();

    @Override
    public @NotNull
    String getId() {
        return "EnumKnow";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getDisplayName() {
        return "Enum Know Setting";
    }

    @Override
    public @Nullable
    JComponent createComponent() {
        return settingUI.getComponent();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        // 从设计的UI中拿到文件的路径
        String url = settingUI.getUrlTextField().getText();
        // 设置文本信息
        try {
            // 下面这些信息将文件读取出来放到对应的UI里面
            File file = new File(url);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(0);
            byte[] bytes = new byte[1024 * 1024];
            int readSize = randomAccessFile.read(bytes);
            byte[] copy = new byte[readSize];
            System.arraycopy(bytes, 0, copy, 0, readSize);
            String str = new String(copy, StandardCharsets.UTF_8);

            // 设置内容，这里全局的一个Config，在ReadUI里面可以使用
        } catch (Exception ignore) {
        }
    }
}
