package com.yyshare.Service;

import com.yyshare.entity.Share;

import java.util.List;
import java.util.Map;

public interface IShareService {

    /**
     * 查询股票数据
     * @param params
     * @return
     */
    List<Share> findShares(Map params);

    /**
     * 完善并保存数据
     * @param params
     */
    void initData(Map params);
}
