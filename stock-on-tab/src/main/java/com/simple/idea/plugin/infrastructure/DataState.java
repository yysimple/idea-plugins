package com.simple.idea.plugin.infrastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:34
 **/
public class DataState {
    private List<String> gids = new ArrayList<>();

    public List<String> getGids() {
        return gids;
    }

    public void setGids(List<String> gids) {
        this.gids = gids;
    }
}
