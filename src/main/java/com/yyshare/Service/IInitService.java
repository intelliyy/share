package com.yyshare.Service;

import java.util.Date;

/**
 * 指标数据初始化服务
 */
public interface IInitService {

    /**
     * 初始化计算指标数据
     * @param startTime
     * @param type
     */
    void init(Date startTime, String type);
}
