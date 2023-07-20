package com.simple.idea.plugin.know.service.impl;

import com.simple.idea.plugin.know.data.CacheInit;
import com.simple.idea.plugin.know.service.EnumKnowService;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 实现
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-17 17:24
 **/
public class EnumKnowServiceImpl implements EnumKnowService {

    @Override
    public String getMessage(String keyword) {
        return CacheInit.search(keyword);
    }
}
