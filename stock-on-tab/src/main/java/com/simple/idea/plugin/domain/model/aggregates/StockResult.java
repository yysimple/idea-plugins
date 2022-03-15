package com.simple.idea.plugin.domain.model.aggregates;

import com.simple.idea.plugin.domain.model.vo.Stock;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-15 16:32
 **/
public class StockResult {
    private Integer resultcode;

    private String reason;

    private Stock[] result;

    public Integer getResultcode() {
        return resultcode;
    }

    public void setResultcode(Integer resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Stock[] getResult() {
        return result;
    }

    public void setResult(Stock[] result) {
        this.result = result;
    }
}
