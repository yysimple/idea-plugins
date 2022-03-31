package com.simple.idea.plugin.mybatis.infrastructure.data;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.simple.idea.plugin.mybatis.domain.model.vo.ORMConfigVO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:55
 **/
@State(name = "DataSetting", storages = @Storage("plugin.xml"))
public class DataSetting implements PersistentStateComponent<DataState> {
    private DataState state = new DataState();

    public static DataSetting getInstance(Project project) {
        return ServiceManager.getService(project, DataSetting.class);
    }

    @Nullable
    @Override
    public DataState getState() {
        return this.state;
    }

    @Override
    public void loadState(@NotNull DataState state) {
        this.state = state;
    }

    public ORMConfigVO getORMConfig() {
        return state.getOrmConfigVO();
    }

    public GenerateOptions getOptions() {
        return state.getOptions();
    }
}
