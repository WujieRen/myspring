package org.myspring.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description
 */
@Slf4j
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    /**
     * 设置类的属性值
     *
     * @param field      成员变量
     * @param target     类实例
     * @param value      成员变量的值
     * @param accessible 是否允许设置私有属性
     */
    public static void setField(Field field, Object target, Object value, boolean accessible){
        field.setAccessible(accessible);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            log.error("setField error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化class
     *
     * @param clazz      Class
     * @param <T>        class的类型
     * @param accessible 是否支持创建出私有class对象的实例
     * @return 类的实例化
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            log.error("newInstance error："+e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取包下类集合
     * 1.这里为什么通过类加载器来加载指定路径下的资源（而不是让用户指定）？
     * 1. 不够友好，不同机器路径可能不同，用户还得去找路径
     * 2. 如果是打的jar包或者war包，很难找到路径
     * 2.类加载器可以定位资源URL
     * classLoader.getResource(The name of a resource is a '/'-separated path name that identifies the resource)
     *
     * @return 类集合
     * @parampackageName包名
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        //1.获取当前类加载器
        ClassLoader classLoader = getClassLoader();
        //2.通过类加载器获取到要加载的目录的URL（统一资源定位符）
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (Objects.isNull(url)) {
            log.warn("unable to retrieve anything from package: " + packageName);
            return null;
        }
        //3.根据不同资源类型，采取不同方式获取资源集合。这里如果是 file 协议类型的才进行处理
        Set<Class<?>> classSet = null;
        if (Objects.equals(url.getProtocol(), FILE_PROTOCOL)) {
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        return classSet;
    }

    /**
     * 递归获取目标package里面的所有class文件(包括子package里的class文件)
     *
     * @param emptyClassSet 装载目标类的集合
     * @param fileSource    文件或者目录
     * @param packageName   包名
     * @return 类集合
     */
    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName) {
        //1. 如果是文件，结束递归
        if (fileSource.isFile()) return;

        //2.过滤出文件夹类型的资源，进行递归处理
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {    //如果是目录，递归处理
                    return true;
                } else {    //如果是文件，加载以.class结尾的字节码文件为Class对象并加入 classSet 集合
                    String absolutePath = file.getAbsolutePath();
                    if (absolutePath.endsWith(".class")) {
                        addToClassSet(absolutePath);
                    }
                }
                return false;
            }

            private void addToClassSet(String absolutePath) {
                absolutePath = absolutePath.replace(File.separator, ".");
                String className = absolutePath.substring(absolutePath.indexOf(packageName), absolutePath.lastIndexOf("."));
                Class<?> targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });

        //3. 递归处理文件夹
        if (files != null) {
            for (File file : files) {
                extractClassFile(emptyClassSet, file, packageName);
            }
        }
    }

    /**
     * 获取Class对象
     *
     * @param className class全名=package + 类名
     * @return Class
     */
    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取classLoader
     *
     * @return 当前ClassLoader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

}
