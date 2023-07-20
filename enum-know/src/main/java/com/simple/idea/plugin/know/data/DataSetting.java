package com.simple.idea.plugin.know.data;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 缓存配置
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-20 15:35
 **/
@State(name = "DataSetting", storages = @Storage("plugin.xml"))
public class DataSetting implements PersistentStateComponent<DataSetting.DataConfig> {

    private DataConfig dataConfig = new DataConfig();

    public static DataSetting getInstance(Project project) {
        return ServiceManager.getService(project, DataSetting.class);
    }

    @Nullable
    @Override
    public DataConfig getState() {
        return this.dataConfig;
    }

    @Override
    public void loadState(@NotNull DataConfig state) {
        this.dataConfig = state;
    }

    public EnumKnowFileConfig getEnumKnowFileConfig() {
        return dataConfig.getEnumKnowFileConfig();
    }

    public EnumKnowDataSourceConfig getEnumKnowDataSourceConfig() {
        return dataConfig.getEnumKnowDataSourceConfig();
    }

    public static class DataConfig {

        private EnumKnowFileConfig enumKnowFileConfig = new EnumKnowFileConfig();

        private EnumKnowDataSourceConfig enumKnowDataSourceConfig = new EnumKnowDataSourceConfig();

        public EnumKnowFileConfig getEnumKnowFileConfig() {
            return enumKnowFileConfig;
        }

        public void setEnumKnowFileConfig(EnumKnowFileConfig enumKnowFileConfig) {
            this.enumKnowFileConfig = enumKnowFileConfig;
        }

        public EnumKnowDataSourceConfig getEnumKnowDataSourceConfig() {
            return enumKnowDataSourceConfig;
        }

        public void setEnumKnowDataSourceConfig(EnumKnowDataSourceConfig enumKnowDataSourceConfig) {
            this.enumKnowDataSourceConfig = enumKnowDataSourceConfig;
        }
    }

    public static class EnumKnowFileConfig {

        /**
         * 选择的路径
         */
        private String enumKnowFilePath;

        public String getEnumKnowFilePath() {
            return enumKnowFilePath;
        }

        public void setEnumKnowFilePath(String enumKnowFilePath) {
            this.enumKnowFilePath = enumKnowFilePath;
        }
    }

    public static class EnumKnowDataSourceConfig {

        /**
         * 数据库用户名
         */
        private String user;

        /**
         * 数据库密码
         */
        private String password;

        /**
         * 下面这些是连接信息
         */
        private String host = "127.0.0.1";

        /**
         * 端口
         */
        private String port = "3306";

        /**
         * 数据库
         */
        private String database;

        /**
         * 表名
         */
        private String tableName;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }

}
