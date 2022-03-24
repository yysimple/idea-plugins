package com.simple.idea.plugin.mvc.module;

import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.simple.idea.plugin.mvc.ui.TestStep2;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-23 18:49
 **/
public class MvcStep2 extends ModuleWizardStep {

    private TestStep2 testStep2;

    public MvcStep2(TestStep2 testStep2) {
        this.testStep2 = testStep2;
    }

    @Override
    public JComponent getComponent() {
        return testStep2.getMainPanel();
    }

    @Override
    public void updateDataModel() {

    }
}
