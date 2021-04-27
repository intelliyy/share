package com.yyshare.dao;

import com.yyshare.config.ConfigProperties;
import com.yyshare.exception.ShareException;
import com.yyshare.spring.SpringContext;
import com.yyshare.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dao执行器
 */
@Component
@Slf4j
public class DaoExecutor {

    private static DaoProxy daoProxy = new DaoProxy();
    private static Map<String, IExecutor> executors = new HashMap<>();

    public static void init(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableListableBeanFactory factory = configurableApplicationContext.getBeanFactory();
        try {
            String path = DaoExecutor.class.getResource("").getPath();
            List<Class> cs = ClassUtil.scan(path, DaoExecutor.class.getPackage().getName());
            for (Class c : cs) {
                if (!c.isInterface() || !c.getName().endsWith("Dao")) {
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

    public static void initExecutors() {
        log.info("开始加载dao执行器");
        ConfigProperties configProperties = SpringContext.getBean(ConfigProperties.class);
        for (Map.Entry<String, String> entry : configProperties.getExecutors().entrySet()) {
            // 初始化执行器
            String name = entry.getKey();
            try {
                Class cl = Class.forName(entry.getValue());
                Constructor constructor = cl.getConstructor();
                IExecutor executor = (IExecutor) constructor.newInstance();
                executors.put(name, executor);
                log.info("dao执行器{}加载完成", name);
            } catch (Exception e) {
                log.warn("dao执行器初始化异常,name={}", name, e);
            }
        }
        // 设置默认执行器
        daoProxy.executor = executors.get(configProperties.getDef());
        log.info("加载dao执行器结束");
    }

    public static void exchangeModel(String name) {

    }

    private static class DaoProxy implements InvocationHandler {
        IExecutor executor;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return executor.invoke(method, args);
        }
    }
}
