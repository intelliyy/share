package com.yyshare.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
    private static AnnotationConfigServletWebServerApplicationContext app;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.app = (AnnotationConfigServletWebServerApplicationContext) applicationContext;
    }

    public static void addBean(String name, Object bean) {
        if (app.containsBeanDefinition(name)) {
            app.removeBeanDefinition(name);
        }
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(bean.getClass());
        app.registerBeanDefinition(name, builder.getBeanDefinition());
        app.getBeanFactory().registerSingleton(name, bean);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return app.getBean(beanClass);
    }
}
