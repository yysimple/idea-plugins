package com.simple.idea.plugin.domain.model.vo;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: k线信息
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:33
 **/
public class GoPicture {

    private String minurl;
    private String dayurl;
    private String weekurl;
    private String monthurl;

    public String getMinurl() {
        return minurl;
    }

    public void setMinurl(String minurl) {
        this.minurl = minurl;
    }

    public String getDayurl() {
        return dayurl;
    }

    public void setDayurl(String dayurl) {
        this.dayurl = dayurl;
    }

    public String getWeekurl() {
        return weekurl;
    }

    public void setWeekurl(String weekurl) {
        this.weekurl = weekurl;
    }

    public String getMonthurl() {
        return monthurl;
    }

    public void setMonthurl(String monthurl) {
        this.monthurl = monthurl;
    }
}
