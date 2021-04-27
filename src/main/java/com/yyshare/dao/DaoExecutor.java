package com.yyshare.dao;

import com.yyshare.exception.ShareException;
import com.yyshare.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * dao执行器
 */
public class DaoExecutor {
    public static String MODEL_EXCEL = "excel";
    public static String MODEL_MYSQL = "mysql";

    private static Logger log = LoggerFactory.getLogger(DaoExecutor.class);
    private static DaoProxy daoProxy = new DaoProxy();

    public static void init(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableListableBeanFactory factory = configurableApplicationContext.getBeanFactory();
        try {
            String path = DaoExecutor.class.getResource("").getPath();
            List<Class> cs = ClassUtil.scan(path, DaoExecutor.class.getPackage().getName());
            for (Class c : cs) {
                if (!c.isInterface()) {
                    continue;
                }
                Object o = Proxy.newProxyInstance(DaoExecutor.class.getClassLoader(),
                        new Class[]{c}, daoProxy);
                factory.registerSingleton(c.getName(), o);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ShareException("dao执行器初始化异常");
        }
    }

    public static void exchangeModel(String model) {

    }

    private static class DaoProxy implements InvocationHandler {
        String model = MODEL_EXCEL;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            
            return null;
        }
    }
}
