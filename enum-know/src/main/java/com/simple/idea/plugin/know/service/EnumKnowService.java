package com.simple.idea.plugin.know.service;

/**
 * 项目: idea-plugins
 *
 * 功能描述: 枚举识别接口
 *
 * @author: WuChengXing
 *
 * @create: 2023-07-17 17:22
 **/
public interface EnumKnowService {

    /**
     * 获取对应的文本值
     * @param keyword
     * @return
     */
    String getMessage(String keyword);
}
