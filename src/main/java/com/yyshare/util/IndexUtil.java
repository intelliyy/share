package com.yyshare.util;

import com.yyshare.config.Config;

/**
 * 指标计算工具
 */
public class IndexUtil {

    public static double ema12(double lema12, double price) {
        return price * 2 / 13 + lema12 * 11 / 13;
    }

    public static double ema26(double lema26, double price) {
        return price * 2 / 27 + lema26 * 25 / 27;
    }

    public static double diff(double ema12, double ema26) {
        return ema12 - ema26;
    }

    public static double dea(double diff, double ldea) {
        return diff / 5 + ldea * 4 / 5;
    }

    public static double macd(double diff, double dea) {
        return (diff - dea) * Config.macd;
    }
}
