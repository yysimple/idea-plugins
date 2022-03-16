package com.simple.idea.plugin.domain.model.vo;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: 股票的详细价格信息
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:33
 **/
public class Data {

    /** 股票ID */
    private String gid;
    /** 股票名称 */
    private String name;
    /** 当前价格 */
    private String nowPri;
    /** 涨跌 */
    private String increase;
    /** 涨幅 */
    private String increPer;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNowPri() {
        return nowPri;
    }

    public void setNowPri(String nowPri) {
        this.nowPri = nowPri;
    }

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getIncrePer() {
        return increPer;
    }

    public void setIncrePer(String increPer) {
        this.increPer = increPer;
    }
}
