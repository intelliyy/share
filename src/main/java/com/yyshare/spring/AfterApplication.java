package com.yyshare.spring;

import com.yyshare.dao.DaoExecutor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AfterApplication implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化各存储执行器组件
        DaoExecutor.initExecutors();
    }
}
