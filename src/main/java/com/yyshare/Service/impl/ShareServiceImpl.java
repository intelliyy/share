package com.yyshare.Service.impl;

import com.yyshare.Service.IShareService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yyshare.constants.Constants;
import com.yyshare.dao.ISharesDao;
import com.yyshare.entity.Share;
import com.yyshare.exception.ShareException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ShareServiceImpl implements IShareService {

    @Autowired
    private ISharesDao sharesDao;

    @Override
    public List<Share> findBaseShares(Map params) {
        String type = (String) params.get("type");
        String start = (String) params.get("start");
        String end = (String) params.get("end");
        if (!Constants.TYPES.contains(type)) {
            throw new ShareException("不支持该类型");
        }
        if (StrUtil.isBlank(end)) {
            return sharesDao.findBaseByStartTime(DateUtil.parse(start, "yyyyMMdd"), new Date(), type);
        }
        return sharesDao.findBaseByStartTime(DateUtil.parse(start, "yyyyMMdd"), DateUtil.parse(end, "yyyyMMdd"), type);
    }
}
