package org.myspring.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.myspring.core.annotation.Component;
import org.myspring.core.annotation.Controller;
import org.myspring.core.annotation.Repository;
import org.myspring.core.annotation.Service;
import org.myspring.util.ClassUtil;
import org.myspring.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author rwj
 * @Date 2022/10/29
 * @Description Bean容器
 *
 *  BeanContainer需要具有的功能和特性：
 *      1. BeanContainer是单例的，全局仅一个
 *      2. 该容器需要有能够保存Class对象及其实例的载体，这里使用 ConcurentHashMap<Class, Object>  —— 线程安全且性能高
 *      3. 容器的加载
 *          1. 配置的管理和获取（这里主要是指定标识注解有哪些）
 *          2. 获取指定路径下的类的Class对象
 *          3. 根据配置（即被指定注解标识的类）提取Class对象，连同其实例一并存入容器（ConcurrentHashMap）中
 *          4. BeanContiner只能初始化一次
 *      4. 对外提供/暴露对容器内部属性进行操作的方式
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    private static final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    private static final Set<Class<? extends Annotation>> BEAN_ANNOTATION =
            new HashSet<>(Arrays.asList(Controller.class, Service.class, Component.class, Repository.class));

    private static Boolean loaded = false;

    public int size() {
        return beanMap.size();
    }

    public boolean isLoaded() {
        return loaded;
    }

    /**
     * 扫描加载所有Bean
     *
     * @param packageName 包名
     */
    public synchronized void loadBeans(String packageName) {
        if(loaded) {
            log.warn("BeanContainer has been loaded.");
            return;
        }
        //1.获取指定路径下的Class对象
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if(ValidationUtil.isEmpty(classSet)) {
            log.warn("extract nothing from packageName" + packageName);
            return;
        }
        //2.根据配置提取指定Class对象，并连同其实例一起放入容器中
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> eClass : BEAN_ANNOTATION) {
                if(clazz.isAnnotationPresent(eClass)) { //如果类上面标记了定义的注解
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));  //将目标类本身作为键，目标类的实例作为值，放入到beanMap中
                }
            }
        }
        loaded = true;
    }

    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private final BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

}
