package com.simple.idea.plugin.xo2xo.infrastructure;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-20 20:07
 **/
@State(name = "DataSetting", storages = @Storage("plugin.xml"))
public class DataSetting  implements PersistentStateComponent<DataSetting.DataState> {
    private DataState state = new DataState();

    public static DataSetting getInstance(Project project) {
        return project.getService(DataSetting.class);
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

    public static class DataState {

        private String selectRadio = "selectExistRadioButton";

        private String configRadio = "show";

        public String getSelectRadio() {
            return selectRadio;
        }

        public void setSelectRadio(String selectRadio) {
            this.selectRadio = selectRadio;
        }

        public String getConfigRadio() {
            return configRadio;
        }

        public void setConfigRadio(String configRadio) {
            this.configRadio = configRadio;
        }
    }
}
