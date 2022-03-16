package com.simple.idea.plugin.domain.service;

import com.simple.idea.plugin.domain.model.vo.Data;
import com.simple.idea.plugin.domain.model.vo.GoPicture;

import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:31
 **/
public interface IStock {
    /**
     * 查询股票的数据
     * @param gids
     * @return
     */
    List<Data> queryPresetStockData(List<String> gids);

    /**
     * 股票的k线
     * @param gid
     * @return
     */
    GoPicture queryGidGoPicture(String gid);
}
