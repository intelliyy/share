package com.yyshare.Service.excel;

import cn.hutool.core.collection.CollectionUtil;
import com.yyshare.Service.IInitService;
import com.yyshare.dao.ISharesDao;
import com.yyshare.entity.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class InitServiceImpl implements IInitService {

    @Autowired
    private ISharesDao sharesDao;

    @Override
    public void computeIfNull(Date startTime, Date endTime, String type) {
        log.info("计算指标并保存，已有值不处理");
        List<Share> shares = sharesDao.findByTime(startTime, endTime, type);
        if (CollectionUtil.isEmpty(shares)) {
            log.info("查无数据");
            return;
        }
        Share[] arr = new Share[shares.size()];
        Arrays.sort(shares.toArray(arr));
        List<Share> ls = new ArrayList<>(arr.length);
        int i = 0;
        for (; i < arr.length; i++) {
            if (!arr[i].inited()) {
                break;
            }
        }
        if (i == 0) {
            arr[0].generateInitShare();
            i = 1;
        }
        for (; i < arr.length; i++) {
            arr[i].computeWithLast(arr[i - 1]);
            ls.add(arr[i]);
        }
        sharesDao.save(ls);
    }

    @Override
    public void transfer(Date startTime, Date endTime, String type) {

    }

    private void baseDataTransfer(Date startTime, Date endTime, String type) {
        
    }
}
