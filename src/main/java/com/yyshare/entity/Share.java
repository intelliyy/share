package com.yyshare.entity;

import lombok.Data;

import java.util.Date;

@Data
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
}
