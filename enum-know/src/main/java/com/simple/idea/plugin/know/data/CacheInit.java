package com.simple.idea.plugin.know.data;

import com.simple.idea.plugin.know.util.DBHelper;
import com.simple.idea.plugin.know.util.PluginUtils;
import com.simple.idea.plugin.know.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目: idea-plugins
 *
 * 功能描述:
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-20 17:38
 **/
public class CacheInit {

    public static Map<String, Object> FILE_CACHE = new HashMap<>(256);
    public static Map<String, Object> DB_CACHE = new HashMap<>(256);

    public static void initCache(String file) {
        if (StringUtils.isEmpty(file)) {
            return;
        }
        loadFile(file);
    }

    private static Map<String, Object> loadFile(String path) {
        BufferedReader br = null;
        try {
            File file = new File(path);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                String[] enumInfo = line.split("#");
                if (enumInfo.length != 2) {
                    continue;
                }
                FILE_CACHE.putIfAbsent(enumInfo[0], enumInfo[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return FILE_CACHE;
    }

    public static String search(String key) {
        return (String) FILE_CACHE.get(key);
    }

    public static String search(String key, String option) {
        if (EnumKnowConstants.READ_FILE.equals(option)) {
            return (String) FILE_CACHE.get(key);
        } else if (EnumKnowConstants.READ_DB.equals(option)) {
            return (String) DB_CACHE.get(key);
        } else if (EnumKnowConstants.READ_ALL.equals(option)) {
            StringBuilder stringBuilder = new StringBuilder();
            String fileShow = "Find By FIle: ";
            String dbShow = "Find By DB: ";
            String fileValue = (String) FILE_CACHE.get(key);
            if (StringUtils.isEmpty(fileValue)) {
                fileValue = "no match value in file";
            }
            String dbValue = (String) DB_CACHE.get(key);
            if (StringUtils.isEmpty(dbValue)) {
                dbValue = "no match value in db";
            }
            stringBuilder.append(fileShow + fileValue);
            stringBuilder.append("\n");
            stringBuilder.append(dbShow + dbValue);
            return stringBuilder.toString();
        }
        return "";

    }

    public static void initCache(DataSetting.EnumKnowDataSourceConfig enumKnowDataSourceConfig) {
        if (!checkDb(enumKnowDataSourceConfig)) {
            return;
        }
        DBHelper dbHelper = new DBHelper(enumKnowDataSourceConfig.getHost(), Integer.parseInt(enumKnowDataSourceConfig.getPort()),
                enumKnowDataSourceConfig.getUsername(), enumKnowDataSourceConfig.getPassword(), enumKnowDataSourceConfig.getDatabase());
        DB_CACHE = dbHelper.resultSet2Map(enumKnowDataSourceConfig.getTableName(), enumKnowDataSourceConfig.getColumn(),
                enumKnowDataSourceConfig.getShowInfo(), enumKnowDataSourceConfig.getSearchPair());

    }

    public static boolean checkDb(DataSetting.EnumKnowDataSourceConfig enumKnowDataSourceConfig) {
        return PluginUtils.allFieldNonNULL(enumKnowDataSourceConfig);
    }
}
