package com.simple.idea.plugin.completion.application;

import com.simple.idea.plugin.completion.domain.model.Node;

import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-16 12:27
 **/
public interface IWordManageService {

    /**
     * 根据前缀查询对应的Node节点
     *
     * @param prefix
     * @return
     */
    List<Node> searchPrefix(String prefix);
}
