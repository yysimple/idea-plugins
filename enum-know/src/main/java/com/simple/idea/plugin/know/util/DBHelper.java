package com.simple.idea.plugin.know.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 数据库操作工具
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-21 10:43
 **/
public class DBHelper {

    private final String host;
    private final Integer port;
    private final String username;
    private final String password;
    private String database;
    private String table;

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

    public ResultSet getResultSet(String tableName, String searchPair, Connection conn) {
        try {
            String sql = buildSql(this.database, tableName, searchPair);
            Statement statement = conn.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String buildSql(String database, String tableName, String searchPair) {
        String sql = "select * from " + database + "." + tableName;
        if (StringUtils.isEmpty(searchPair)) {
            return sql;
        }
        JSONObject jsonObject = null;
        StringBuilder buildSql = null;
        try {
            jsonObject = JSONObject.parseObject(searchPair);
            buildSql = new StringBuilder(sql);
            int size = jsonObject.size();
            int andNum = size - 1;
            if (size > 0) {
                buildSql.append(" where");
                for (String key : jsonObject.keySet()) {
                    String[] multiValues = jsonObject.getObject(key, String.class).split(",");
                    if (Objects.nonNull(multiValues) && multiValues.length > 0 && StringUtils.isNotEmpty(multiValues[0])) {
                        buildSql.append(" " + key + " in (");
                        int commaNum = multiValues.length - 1;
                        for (String multiValue : multiValues) {
                            buildSql.append("'" + multiValue + "'");
                            if (commaNum > 0) {
                                buildSql.append(",");
                                commaNum--;
                            }
                        }
                        buildSql.append(")");
                        if (andNum > 0) {
                            buildSql.append(" and");
                            andNum--;
                        } else {
                            buildSql.append(";");
                        }
                    } else {
                        andNum--;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return buildSql.toString();
    }


    public static void main(String[] args) {
        String json = "{\"id\": \"1,2,2,3\", \"name\":\"\", \"age\": \"18\"}";
        System.out.println(buildSql("test", "test", json));
    }

    public ResultSet getResultSet(String tableName) {
        Connection conn = getConnection(this.database);
        try {
            String sql = "select * from " + database + "." + tableName;
            Statement statement = conn.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            closeConnection(conn);
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

    public <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 每次遍历行值
            while (resultSet.next()) {
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if (value instanceof Timestamp) {
                        method = clazz.getMethod(setMethod, Date.class);
                    } else {
                        method = clazz.getMethod(setMethod, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Map<String, Object> resultSet2Map(String tableName, String columnName, String showInfo, String searchPair) {
        Connection conn = getConnection(this.database);
        ResultSet resultSet = this.getResultSet(tableName, searchPair, conn);
        Map<String, Object> cache = new HashMap<>(128);
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            // 每次遍历行值
            while (resultSet.next()) {
                StringBuilder stringBuilder = new StringBuilder();
                String cacheKey = "";
                String cacheValue = "";
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String dbColumnName = metaData.getColumnName(i);
                    if (dbColumnName.equals(columnName)) {
                        cacheKey = value.toString();
                    } else {
                        if (StringUtils.isNotEmpty(showInfo)) {
                            String[] showColumns = showInfo.split(",");
                            List<String> showColumnList = Arrays.asList(showColumns);
                            if (showColumnList.contains(dbColumnName)) {
                                stringBuilder.append(value.toString() + ";");
                            }
                        } else {
                            stringBuilder.append(value.toString() + ";");
                        }
                    }
                }
                cacheValue = stringBuilder.toString();
                cache.putIfAbsent(cacheKey, cacheValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return cache;
    }
}
