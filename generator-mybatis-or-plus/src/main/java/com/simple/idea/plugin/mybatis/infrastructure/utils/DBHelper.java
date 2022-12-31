package com.simple.idea.plugin.mybatis.infrastructure.utils;

import com.simple.idea.plugin.mybatis.infrastructure.po.Column;
import com.simple.idea.plugin.mybatis.infrastructure.po.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-19 11:09
 **/
public class DBHelper {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String database;

    private Properties properties;

    public DBHelper(String host, Integer port, String username, String password, String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        properties = new Properties();
        properties.put("user", this.username);
        properties.put("password", this.password);
        properties.setProperty("remarks", "true");
        properties.put("useInformationSchema", "true");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false", properties);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Connection getConnection(String database) {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + database + "?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false", properties);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public List<String> getDatabases() {
        Connection conn = getConnection();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet catalogs = metaData.getCatalogs();
            List<String> rs = new ArrayList<>();
            while (catalogs.next()) {
                String db = catalogs.getString("TABLE_CAT");
                if (org.apache.commons.lang3.StringUtils.equalsIgnoreCase(db, "information_schema")) {
                    continue;
                }
                rs.add(db);
            }
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 查询数据库里面所有的表名
     *
     * @param database
     * @return
     */
    public List<String> getAllTableName(String database) {
        this.database = database;
        Connection conn = getConnection(this.database);
        try {
            String sql = "select table_name from information_schema.tables where table_schema='" + database + "'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<String> ls = new ArrayList<>();
            while (rs.next()) {
                String s = rs.getString("TABLE_NAME");
                ls.add(s);
            }
            return ls;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 查询数据库里面所有的表名
     *
     * @param database
     * @return
     */
    public List<String> getAllTableName(String database, String keyword) {
        this.database = database;
        Connection conn = getConnection(this.database);
        try {
            String sql = "select table_name from information_schema.tables where table_schema='" + database + "'" 
                    + "and table_name like '%" + keyword + "%'";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            List<String> ls = new ArrayList<>();
            while (rs.next()) {
                String s = rs.getString("TABLE_NAME");
                ls.add(s);
            }
            return ls;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    public Table getTable(String tableName) {
        Connection conn = getConnection(this.database);
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, "", tableName, new String[]{"TABLE"});
            Table table = null;
            if (rs.next()) {
                table = new Table(rs.getString("REMARKS"), tableName, getAllColumn(tableName, conn));
            }
            return table;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
        }
    }

    private List<Column> getAllColumn(String tableName, Connection conn) {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            String primaryKey = null;
            while (primaryKeys.next()) {
                primaryKey = primaryKeys.getString("COLUMN_NAME");
            }
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            List<Column> ls = new ArrayList<>();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                Column column = new Column(rs.getString("REMARKS"), columnName, rs.getInt("DATA_TYPE"));
                if (columnName.equals(primaryKey)) {
                    column.setId(true);
                }
                ls.add(column);
            }
            return ls;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 这里的话是UI界面上面的按钮来触发此操作的，进入此处验证数据库是否连接
     *
     * @return
     */
    public String testDatabase() {
        Connection conn = getConnection();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT VERSION() AS MYSQL_VERSION");
            ResultSet resultSet = null;
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("MYSQL_VERSION");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return "Err";
    }
}
