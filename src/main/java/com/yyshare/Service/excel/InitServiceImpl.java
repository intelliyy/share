package com.yyshare.Service.excel;

import com.yyshare.Service.IInitService;
import com.yyshare.dao.ISharesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InitServiceImpl implements IInitService {

    @Autowired
    private ISharesDao sharesDao;

    @Override
    public void init(Date startTime, String type) {

    }
}
