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
    List<Data> queryPresetStockData(List<String> gids);

    GoPicture queryGidGoPicture(String gid);
}
