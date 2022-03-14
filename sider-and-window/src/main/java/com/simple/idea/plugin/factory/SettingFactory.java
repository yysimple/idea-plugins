package com.simple.idea.plugin.factory;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.simple.idea.plugin.Config;
import com.simple.idea.plugin.ui.SettingUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 * 实现自 SearchableConfigurable 接口的方法比较多，包括：getId、getDisplayName、createComponent、isModified、apply
 * 这些里面用于写逻辑实现的主要是 createComponent 和 apply createComponent 方法主要是把我们自己创建的 UI 面板提供给 JComponent
 * apply 是一个事件，当我们点击完成配置的 OK、完成，时候就会触发到这个方法。
 * 在这个方法中我们拿到文件的 URL 地址使用 RandomAccessFile 进行读取解析文件，
 * 并最终把文件内容展示到阅读窗体中 Config.readUI.getTextContent().setText(str);
 *
 * @author: WuChengXing
 * @create: 2022-02-24 13:34
 **/
public class SettingFactory implements SearchableConfigurable {

    private SettingUI settingUI = new SettingUI();

    @Override
    public @NotNull
    String getId() {
        return "test.id";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title)
    String getDisplayName() {
        return "test-config";
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
            Config.readUI.getTextContent().setText(str);
        } catch (Exception ignore) {
        }
    }
}
