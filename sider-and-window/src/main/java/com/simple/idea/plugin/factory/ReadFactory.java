package com.simple.idea.plugin.factory;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.simple.idea.plugin.Config;
import com.simple.idea.plugin.ui.ReadUI;
import org.jetbrains.annotations.NotNull;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 * - 接口方法 ToolWindowFactory#createToolWindowContent 是需要自己工具框类实现的方法，
 * - 在这个 createToolWindowContent 方法中把自己的窗体 ReadUI 实例化后填充进去即可。
 * - 添加窗体的补助主要依赖于 ContentFactory.SERVICE.getInstance() 创建出 ContentFactory 并最终使用 toolWindow 添加窗体显示 UI 即可。
 * - 这里我们额外的还添加了一个全局属性 Config.readUI 这是为了后续可以在配置窗体中使用这个 UI 进行设置文件内容。
 *
 * @author: WuChengXing
 * @create: 2022-02-24 13:31
 **/
public class ReadFactory implements ToolWindowFactory {
    private ReadUI readUI = new ReadUI();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        // 获取 ToolWindow 显示的内容
        Content content = contentFactory.createContent(readUI.getComponent(), "", false);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content);
        // 全局使用
        Config.readUI = readUI;
    }
}
