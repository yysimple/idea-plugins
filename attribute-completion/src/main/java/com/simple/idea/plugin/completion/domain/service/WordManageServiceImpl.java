package com.simple.idea.plugin.completion.domain.service;

import com.simple.idea.plugin.completion.application.IWordManageService;
import com.simple.idea.plugin.completion.domain.model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-16 12:31
 **/
public class WordManageServiceImpl extends AbstractWordManage implements IWordManageService {
    @Override
    public List<Node> searchPrefix(String prefix) {
        // 拿到单词树 a -> z
        Node root = wordsTree;
        // 拿到前缀信息
        char[] chars = prefix.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 判断是不是
        for (char aChar : chars) {
            int charIndex = aChar - 'a';
            if (charIndex > root.slot.length || charIndex < 0 || root.slot[charIndex] == null) {
                return Collections.emptyList();
            }
            sb.append(aChar);
            root = root.slot[charIndex];
        }

        ArrayList<Node> list = new ArrayList<>();
        if (root.prefix != 0) {
            for (int i = 0; i < root.slot.length; i++) {
                if (root.slot[i] != null) {
                    char c = (char) (i + 'a');
                    collect(root.slot[i], String.valueOf(sb) + c, list, RESULT_LIMIT);
                    if (list.size() >= RESULT_LIMIT) {
                        return list;
                    }
                }
            }
        }
        return list;
    }
}
