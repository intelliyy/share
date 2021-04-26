package com.yyshare.spring;

import com.yyshare.dao.DaoExecutor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ApplicationInit implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        // 初始化dao层代理bean
        DaoExecutor.init(configurableApplicationContext);
    }
}
