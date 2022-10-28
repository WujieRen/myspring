package org.myspring.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description
 */
public class ClassUtilTest {


    @DisplayName("提取目标类方法：extractPackageClassTest")
    @Test
    public void extractPackageClassTest() {
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("cn.rwj.fakeprj.entity");
        for (Class<?> aClass : classSet) {
            System.out.println(aClass);
        }
        Assertions.assertEquals(4, classSet.size());
    }

}
