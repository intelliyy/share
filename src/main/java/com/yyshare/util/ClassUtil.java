package com.yyshare.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

    /**
     * 扫描指定包下的类
     * @param packagePath
     * @param packageName
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Class> scan(String packagePath, String packageName) throws ClassNotFoundException {
        File[] fs = new File(packagePath).listFiles();
        List<Class> cs = new ArrayList<>(fs.length);
        for (File f : fs) {
            String name = f.getName();
            if (name.endsWith(".class") && !name.contains("$")) {
                cs.add(Class.forName(packageName + "." + name.substring(0, name.indexOf("."))));
            }
        }
        return cs;
    }
}
