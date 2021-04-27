package com.yyshare.dao;

import com.yyshare.entity.Share;

import java.util.Date;
import java.util.List;

public interface ISharesDao {

    /**
     * 查询股票基本数据（股价信息，不含指标信息）
     * @param start
     * @param end
     * @param type
     * @return
     */
    List<Share> findByTime(Date start, Date end, String type);

    /**
     * 保存
     * @param shares
     */
    void save(List<Share> shares);
}
