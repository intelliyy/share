package com.yyshare.dao.excel;

import com.yyshare.dao.IExecutor;
import com.yyshare.exception.ShareException;
import com.yyshare.util.ClassUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * excel存储组件执行器
 */
public class ExcelExecutor implements IExecutor {

    {
        try {
            String path = ExcelExecutor.class.getResource("").getPath();
            List<Class> cs = ClassUtil.scan(path, ExcelExecutor.class.getPackage().getName());
            for (Class c : cs) {
                if (c == ExcelExecutor.class) {
                    continue;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShareException("excel存储组件初始化异常");
        }
    }

    @Override
    public Object invoke(Method method, Object[] args) {
        return null;
    }
}
