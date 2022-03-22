package com.simple.idea.plugin.xo2xo.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ui.Messages;
import com.simple.idea.plugin.xo2xo.application.IGenerateXxo2Xxo;
import com.simple.idea.plugin.xo2xo.domain.service.impl.GenerateXxo2XxoImpl;
import org.jetbrains.annotations.NotNull;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-20 20:06
 **/
public class Xxo2XxoGenerateAction extends AnAction {

    private IGenerateXxo2Xxo generateVo2Dto = new GenerateXxo2XxoImpl();

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            // 织入代码，也就是生成新代码的主流程
            generateVo2Dto.doGenerate(event.getProject(), event.getDataContext(), event.getData(LangDataKeys.PSI_FILE));
        } catch (Exception e) {
            Messages.showErrorDialog(event.getProject(), "请按规：先复制对象后，例如：A a，再光标放到需要织入的对象上，例如：B b！", "错误提示");
        }
    }
}
