package com.simple.idea.plugin.mvc.factory;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.platform.ProjectTemplate;
import com.intellij.platform.ProjectTemplatesFactory;
import com.simple.idea.plugin.mvc.infrastructure.ICONS;
import com.simple.idea.plugin.mvc.module.MvcModuleBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-17 19:37
 **/
public class TemplateFactory extends ProjectTemplatesFactory {
    @NotNull
    @Override
    public String[] getGroups() {
        return new String[]{"MVC脚手架"};
    }

    @Override
    public Icon getGroupIcon(String group) {
        return ICONS.DDD;
    }

    @NotNull
    @Override
    public ProjectTemplate[] createTemplates(@Nullable String group, WizardContext context) {
        return new ProjectTemplate[]{new BuilderBasedTemplate(new MvcModuleBuilder())};
    }

    private class BuilderBasedTemplate implements ProjectTemplate {
        private final ModuleBuilder myBuilder;

        public BuilderBasedTemplate(ModuleBuilder builder) {
            myBuilder = builder;
        }

        @NotNull
        @Override
        public String getName() {
            return myBuilder.getPresentableName();
        }

        @Nullable
        @Override
        public String getDescription() {
            return myBuilder.getDescription();
        }

        @Override
        public Icon getIcon() {
            return myBuilder.getNodeIcon();
        }

        @NotNull
        @Override
        public ModuleBuilder createModuleBuilder() {
            return myBuilder;
        }

        @Nullable
        @Override
        public ValidationInfo validateSettings() {
            return null;
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
