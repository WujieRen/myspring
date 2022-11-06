package org.myspring.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.myspring.aop.annotation.Aspect;
import org.myspring.aopj.annotation.AspectJ;
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
 *          1. 增加、删除Bean操作
 *          2. 根据Class获取对应实例
 *          3. 获取所有的Class集合
 *          4. 获取所有的实例集合
 *          5. 根据注解获取出被注解标识的Class集合
 *          6. 通过超类获取对应的子类Class结合，不包括其本身
 *          7. 获取容器载体保存Class的数量
 *
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    private static final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    private static final Set<Class<? extends Annotation>> BEAN_ANNOTATION =
            new HashSet<>(Arrays.asList(Controller.class, Service.class, Component.class, Repository.class, Aspect.class, AspectJ.class));

    private static Boolean loaded = false;

    public int size() {
        return beanMap.size();
    }

    public boolean isLoaded() {
        return loaded;
    }

    private Set<Class<?>> validatedGetClasses() {
        Set<Class<?>> keySet = getClasses();
        if(ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }
        return keySet;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，不包括其本身
     *
     * @param interfaceOrClass 接口Class或者父类Class
     * @return Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        Set<Class<?>> keySet = validatedGetClasses();
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            if(interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 根据注解筛选出Bean的Class集合
     *
     * @param annotation 注解
     * @return Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        Set<Class<?>> keySet = validatedGetClasses();
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            if(clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 获取所有Bean集合
     *
     * @return Class集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 获取容器管理的所有Class对象集合
     *
     * @return Class集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 根据Class对象获取Bean实例
     *
     * @param clazz Class对象
     * @return Bean实例
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 移除一个IOC容器管理的对象
     *
     * @param clazz Class对象
     * @return 删除的Bean实例, 没有则返回null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 添加一个class对象及其Bean实例
     *
     * @param clazz Class对象
     * @param bean  Bean实例
     * @return 原有的Bean实例, 没有则返回null
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
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
