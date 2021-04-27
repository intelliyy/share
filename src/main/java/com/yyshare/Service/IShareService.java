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
}
