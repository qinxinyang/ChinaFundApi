package com.xinyang.fund.entity;

import java.util.Date;

/**
 * 净值
 */
public class WorthItem {
    /**
     * 净值日志
     */
    private Date date;
    /**
     * 时间戳日期
     */
    private long longDate;

    /**
     * 净值
     */
    private String netWorth;
    /**
     * 累计净值
     */
    private String acWorth;

    public long getLongDate() {
        return longDate;
    }

    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }

    public Date getDate() {
        return new Date(this.getLongDate());
    }

    public String getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(String netWorth) {
        this.netWorth = netWorth;
    }

    public String getAcWorth() {
        return acWorth;
    }

    public void setAcWorth(String acWorth) {
        this.acWorth = acWorth;
    }

    @Override
    public String toString() {
        return "WorthItem{" +
                "date=" + this.getDate() +
                ", longDate=" + longDate +
                ", netWorth='" + netWorth + '\'' +
                ", acWorth='" + acWorth + '\'' +
                '}';
    }
}
