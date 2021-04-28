package com.yyshare.Service;

import java.util.Date;

/**
 * 指标数据计算服务
 */
public interface IInitService {

    /**
     * 计算指标数据并保存
     * @param startTime
     * @param type
     */
    void computeIfNull(Date startTime, Date endTime, String type);

    /**
     * 迁移基本数据保存到数据库
     * @param startTime
     * @param endTime
     * @param type
     */
    void transfer(Date startTime, Date endTime, String type);
}
