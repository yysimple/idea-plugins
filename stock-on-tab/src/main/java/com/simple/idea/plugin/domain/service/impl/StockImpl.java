package com.simple.idea.plugin.domain.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.simple.idea.plugin.domain.model.aggregates.StockResult;
import com.simple.idea.plugin.domain.model.vo.Data;
import com.simple.idea.plugin.domain.model.vo.GoPicture;
import com.simple.idea.plugin.domain.model.vo.Stock;
import com.simple.idea.plugin.domain.service.IStock;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:31
 **/
public class StockImpl implements IStock {

    /**
     * 自行申请，股票API，替换key即可【一天可免费调用100次】：https://dashboard.juhe.cn/home/
     */
    private final String key = "f664fc427107fac114e10808a5233c72";

    @Override
    public List<Data> queryPresetStockData(List<String> gids) {
        List<Data> dataList = new ArrayList<>();
        for (String gid : gids) {
            StockResult stockResult = JSON.parseObject(get(gid), StockResult.class);
            Stock[] stocks = stockResult.getResult();
            // 组装对应的数据
            for (Stock stock : stocks) {
                dataList.add(stock.getData());
            }
        }
        return dataList;
    }

    @Override
    public GoPicture queryGidGoPicture(String gid) {
        StockResult stockResult = JSON.parseObject(get(gid), StockResult.class);
        Stock[] stocks = stockResult.getResult();
        // 这里是返回的gif图，也就是k线，有对应的日k等
        return stocks[0].getGoPicture();
    }

    /**
     * 数据查询，这里就是调用三方api，然后然拿到对应的数据
     *
     * @param gid
     * @return
     */
    private String get(String gid) {
        // 具体数据文件：resources/juhe.json
        return HttpUtil.get("http://web.juhe.cn:8080/finance/stock/hs?gid=" + gid + "&key=" + key);
    }
}
