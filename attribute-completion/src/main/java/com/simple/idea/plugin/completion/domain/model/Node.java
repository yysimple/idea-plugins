package com.simple.idea.plugin.completion.domain.model;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-16 12:27
 **/
public class Node {
    /**
     * 形成一个链
     */
    public Node[] slot = new Node[26];

    /**
     * 字母
     */
    public char c;

    /**
     * 数量
     */
    public int count;

    /**
     * 前缀
     */
    public int prefix;

    /**
     * 单词
     */
    public String word;

    /**
     * 单词解释
     */
    public String explain;
}
