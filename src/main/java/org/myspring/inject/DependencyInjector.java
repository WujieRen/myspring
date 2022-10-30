package org.myspring.inject;

import lombok.extern.slf4j.Slf4j;
import org.myspring.core.BeanContainer;
import org.myspring.inject.annotation.Autowired;
import org.myspring.util.ClassUtil;
import org.myspring.util.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @Author rwj
 * @Date 2022/10/30
 * @Description 依赖注入器
 *
 *  实现：
 *      在BeanContainer初始化并Loaded Bean 后，
 *          1. 获取所有容器中的的Class对象
 *          2. 遍历Class对象的成员变量
 *          3. 找出被@Autowired标记的成员变量
 *              1. 获取这些成员变量的类型
 *              2. 获取容器中该类型对应的实例
 *              3. 用反射，给这些变量赋值为找到的实例，即注入
 *
 */
@Slf4j
public class DependencyInjector {

    private BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    public void doIoc() {
        //1.获取所有容器中的的Class对象
        Set<Class<?>> classSet = beanContainer.getClasses();
        if(ValidationUtil.isEmpty(classSet)) {
            log.warn("empty classset in BeanContainer");
            return;
        }
        //2.遍历Class对象的成员变量
        for (Class<?> clazz : classSet) {
            Field[] fields = clazz.getDeclaredFields();
            if(ValidationUtil.isEmpty(fields)) {
                continue;
            }

            for (Field field : fields) {
                //3.如果成员变量被Autowired标记了
                if(field.isAnnotationPresent(Autowired.class)) {
                    Autowired annotation = field.getAnnotation(Autowired.class);
                    String autowiredValue = annotation.value();

                    //4.获取成员变量的类型
                    Class<?> fieldClass = field.getType();
                    //5.获取成员变量的类型在容器里对应的实例
                    Object fieldValue = getFieldInstance(fieldClass, autowiredValue);
                    if(fieldValue == null) {
                        throw new RuntimeException("unable to inject relevant type，target fieldClass is:" + fieldClass.getName() + " autowiredValue is : " + autowiredValue);
                    } else {
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, targetBean, fieldValue, true);
                    }
                }
            }
        }
    }

    /**
     * 根据成员变量的类型，获取成员变量的类型在容器里对应的实例
     *
     * @param fieldClass    成员变量的类型
     * @param autowiredValue    注解的value
     * @return
     */
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        //1.获取BeanContainer中该class对应的Bean
        Object fieldValue = beanContainer.getBean(fieldClass);
        if(fieldValue == null) {  //如果是null，说明该类型是一个接口或者父类标识类型，需要去BeanContainer中遍历，找出该接口或父类的所有实现类Bean
            Class<?> implementedClass = getImplementedClass(fieldClass, autowiredValue);    //获取该接口或父类在BeanContainer中的实现类
            if(implementedClass == null) {  //容器中没找到该类的实现类
                return null;
            }
            return beanContainer.getBean(implementedClass);
        }
        return fieldValue;    //不是null说明已经获取到容器中该Class对应的实例
    }

    /**
     *  获取接口的实现类
     * @param fieldClass    接口或父类Class
     * @param autowiredValue    @Autowired注入的值(即实现类的名称)
     * @return
     */
    private Class<?> getImplementedClass(Class<?> fieldClass, String autowiredValue) {
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(fieldClass);
        if(!ValidationUtil.isEmpty(classSet)) {
            if(ValidationUtil.isEmpty(autowiredValue)) {
                if(classSet.size() == 1) {
                    return classSet.iterator().next();
                } else {
                    throw new RuntimeException("multiple implemented classes for " + fieldClass.getName() + " please set @Autowired's value to pick one");
                }
            } else {
                for (Class<?> clazz : classSet) {
                    if(clazz.getSimpleName().equals(autowiredValue)) {
                        return clazz;
                    }
                }
            }
        }
        throw new RuntimeException("No implemented interface or parent class " + fieldClass + "has found.");
    }

}
