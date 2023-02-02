package com.xinyang.fund.entity;

import java.util.List;

public class Fund {
    /**
     * 基金名称
     */
    private String chineseName;
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金净值列表
     */
    private List<WorthItem> worthItems;

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public List<WorthItem> getWorthItems() {
        return worthItems;
    }

    public void setWorthItems(List<WorthItem> worthItems) {
        this.worthItems = worthItems;
    }

    @Override
    public String toString() {
        return "Fund{" +
                "chineseName='" + chineseName + '\'' +
                ", fundCode='" + fundCode + '\'' +
                ", worthItems=" + worthItems +
                '}';
    }
}
