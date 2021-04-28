package com.yyshare.entity;

import cn.hutool.core.date.DateUtil;
import com.yyshare.util.IndexUtil;
import lombok.Data;

import java.util.Date;

@Data
public class Share implements Comparable<Share> {

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

    public Share(Double price, String type, Date time) {
        this.price = price;
        this.type = type;
        this.time = time;
    }

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

    @Override
    public int compareTo(Share o) {
        return DateUtil.compare(time, o.time);
    }

    public void generateInitShare() {
        this.ema12 = this.price;
        this.ema26 = this.price;
        this.diff = 0d;
        this.dea = 0d;
        this.macd = 0d;
    }

    public boolean inited() {
        return this.macd != null && this.dea != null && this.diff != null && this.ema26 != null && this.ema12 != null;
    }

    public void computeWithLast(Share last) {
        this.ema12 = IndexUtil.ema12(last.ema12, this.price);
        this.ema26 = IndexUtil.ema26(last.ema26, this.price);
        this.diff = IndexUtil.diff(this.ema12, this.ema26);
        this.dea = IndexUtil.dea(this.diff, last.dea);
        this.macd = IndexUtil.macd(this.diff, this.dea);
    }
}
