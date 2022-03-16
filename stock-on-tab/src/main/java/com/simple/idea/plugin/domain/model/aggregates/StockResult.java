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

    /**
     * 返回状态码
     */
    private Integer resultcode;

    /**
     * 原因
     */
    private String reason;

    /**
     * 返回结果，这里是个数组，可以查询多只股票
     */
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
