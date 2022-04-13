package com.simple.idea.plugin.probe;

import cn.hutool.core.util.StrUtil;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.JavaProgramPatcher;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.JavaSdkVersion;
import com.intellij.openapi.projectRoots.Sdk;
import com.simple.idea.plugin.probe.util.PluginUtil;

import java.util.Objects;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-13 19:49
 **/
public class SqlProbeRun extends JavaProgramPatcher {

    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters) {

        if (!(configuration instanceof RunConfiguration)) {
            return;
        }

        Sdk jdk = javaParameters.getJdk();

        if (Objects.isNull(jdk)) {
            return;
        }

        JavaSdkVersion version = JavaSdk.getInstance().getVersion(jdk);

        if (Objects.isNull(version)) {
            return;
        }

        if (version.compareTo(JavaSdkVersion.JDK_1_8) < 0) {
            return;
        }

        String agentCoreJarPath = PluginUtil.getAgentCoreJarPath();

        if (StrUtil.isBlank(agentCoreJarPath)) {
            return;
        }

        RunConfiguration runConfiguration = (RunConfiguration) configuration;
        ParametersList vmParametersList = javaParameters.getVMParametersList();
        vmParametersList.addParametersString("-javaagent:" + agentCoreJarPath);
        vmParametersList.addNotEmptyProperty("bytecode-sql-probe.projectId", runConfiguration.getProject().getLocationHash());

    }
}
