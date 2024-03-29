package com.simple.idea.plugin.know.active;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.simple.idea.plugin.know.data.CacheInit;
import com.simple.idea.plugin.know.data.DataSetting;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 用于初始化加载
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-20 21:51
 **/
public class EnumKnowPluginStartupActivity implements StartupActivity {

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public void runActivity(@NotNull Project project) {
        // === 初始化文件数据 ===
        executorService.submit(() -> {
            DataSetting.EnumKnowFileConfig enumKnowFileConfig = DataSetting.getInstance(project).getState().getEnumKnowFileConfig();
            System.out.println("启动项目：" + enumKnowFileConfig.getEnumKnowFilePath());
            CacheInit.initCache(enumKnowFileConfig.getEnumKnowFilePath());
        });

        // === 初始化mysql数据 ===
        executorService.submit(() -> {
            DataSetting.EnumKnowDataSourceConfig enumKnowDataSourceConfig = DataSetting.getInstance(project).getState().getEnumKnowDataSourceConfig();
            CacheInit.initCache(enumKnowDataSourceConfig);
        });

    }
}
