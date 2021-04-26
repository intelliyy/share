package com.yyshare.dao;

import java.lang.reflect.Method;

/**
 * 执行器
 */
public interface IExecutor {

    /**
     * dao执行
     * @param method
     * @param args
     * @return
     */
    Object invoke(Method method, Object[] args);
}
