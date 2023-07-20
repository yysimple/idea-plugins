package com.simple.idea.plugin.know.data;

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
                FILE_CACHE.put(enumInfo[0], enumInfo[1]);
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

}
