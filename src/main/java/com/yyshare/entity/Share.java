package com.yyshare.entity;

import java.util.Date;

public class Share {

    private Double price;
    private Double macd;
    private Double diff;
    private Double dea;
    private Double ema12;
    private Double ema26;
    /**
     * 30,120,101
     */
    private String type;
    private Date time;

    public Share() {}

    public Share(Double price, Double macd, Double diff, Double dea, Double ema12, Double ema26, String type, Date time) {
        this.price = price;
        this.macd = macd;
        this.diff = diff;
        this.dea = dea;
        this.ema12 = ema12;
        this.ema26 = ema26;
        this.type = type;
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMacd() {
        return macd;
    }

    public void setMacd(Double macd) {
        this.macd = macd;
    }

    public Double getDiff() {
        return diff;
    }

    public void setDiff(Double diff) {
        this.diff = diff;
    }

    public Double getDea() {
        return dea;
    }

    public void setDea(Double dea) {
        this.dea = dea;
    }

    public Double getEma12() {
        return ema12;
    }

    public void setEma12(Double ema12) {
        this.ema12 = ema12;
    }

    public Double getEma26() {
        return ema26;
    }

    public void setEma26(Double ema26) {
        this.ema26 = ema26;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
