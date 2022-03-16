package com.simple.idea.plugin.domain.model.vo;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 股票信息
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:33
 **/
public class Stock {

    private Data data;
    private GoPicture goPicture;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public GoPicture getGoPicture() {
        return goPicture;
    }

    public void setGoPicture(GoPicture goPicture) {
        this.goPicture = goPicture;
    }
}
