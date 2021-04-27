package com.yyshare.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface Constants {

    String EXCEL_DATA_PATH = "C:\\Users\\yuanye\\股票数据\\创业板\\数据\\";

    String TYPE_30 = "30";
    String TYPE_120 = "120";
    String TYPE_101 = "101";
    /**
     * 支持的macd类型
     */
    Set<String> TYPES = new HashSet<>(Arrays.asList("30", "120", "101"));
}
