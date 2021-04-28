package com.yyshare.dao.excel;

import com.yyshare.dao.IExecutor;
import com.yyshare.exception.ShareException;
import com.yyshare.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel存储组件执行器
 */
@Slf4j
public class ExcelExecutor implements IExecutor {

    private Map<String, Object> impls = new HashMap<>();

    {
        try {
            String path = ExcelExecutor.class.getResource("").getPath();
            List<Class> cs = ClassUtil.scan(path, ExcelExecutor.class.getPackage().getName());
            for (Class c : cs) {
                if (c == ExcelExecutor.class) {
                    continue;
                }
                Object obj = c.getConstructor().newInstance();
                Class[] infs = c.getInterfaces();
                for (Class inf : infs) {
                    impls.put(inf.getName(), obj);
                }
            }
        } catch (Exception e) {
            log.warn("excel存储组件初始化异常", e);
            throw new ShareException("excel存储组件初始化异常");
        }
    }

    @Override
    public Object invoke(Method method, Object[] args) {
        Object obj = getInvokeObj(method);
        if (obj == null) {
            log.info("excel存储组件不支持此方法");
            return null;
        }
        try {
            return method.invoke(obj, args);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t instanceof ShareException) {
                throw (ShareException) t;
            }
            log.error("excel存储执行器出现未知异常", e);
            throw new ShareException("excel存储执行器出现未知异常");
        } catch (Exception e) {
            log.error("excel存储执行器出现未知异常", e);
            throw new ShareException("excel存储执行器出现未知异常");
        }
    }

    private Object getInvokeObj(Method method) {
        return impls.get(method.getDeclaringClass().getName());
    }
}
