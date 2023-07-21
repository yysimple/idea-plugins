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

    public EnumKnowOptionsConfig getEnumKnowOptionsConfig() {
        return dataConfig.getEnumKnowOptionsConfig();
    }

    public static class DataConfig {

        private EnumKnowFileConfig enumKnowFileConfig = new EnumKnowFileConfig();

        private EnumKnowDataSourceConfig enumKnowDataSourceConfig = new EnumKnowDataSourceConfig();

        private EnumKnowOptionsConfig enumKnowOptionsConfig = new EnumKnowOptionsConfig();

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

        public EnumKnowOptionsConfig getEnumKnowOptionsConfig() {
            return enumKnowOptionsConfig;
        }

        public void setEnumKnowOptionsConfig(EnumKnowOptionsConfig enumKnowOptionsConfig) {
            this.enumKnowOptionsConfig = enumKnowOptionsConfig;
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

    public static class EnumKnowOptionsConfig {

        /**
         * 读取模式：1=读文件；2=读db；3=两者都读；
         */
        private String readMode;

        public String getReadMode() {
            return readMode;
        }

        public void setReadMode(String readMode) {
            this.readMode = readMode;
        }
    }

    public static class EnumKnowDataSourceConfig {

        /**
         * 数据库用户名
         */
        private String username;

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

        /**
         * 列名
         */
        private String column;

        /**
         * 需要展示的信息，列名eg：id,name,active
         */
        private String showInfo;

        /**
         * 搜索条件：{"code": "FH001.01", "code_name": "水果"}
         */
        private String searchPair;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getShowInfo() {
            return showInfo;
        }

        public void setShowInfo(String showInfo) {
            this.showInfo = showInfo;
        }

        public String getSearchPair() {
            return searchPair;
        }

        public void setSearchPair(String searchPair) {
            this.searchPair = searchPair;
        }
    }

}
