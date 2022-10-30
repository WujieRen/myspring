package org.myspring.inject;

import cn.rwj.fakeprj.controller.frontend.MainPageController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.myspring.core.BeanContainer;

/**
 * @Author rwj
 * @Date 2022/10/30
 * @Description
 */
public class DependencyInjkectorTest {

    @DisplayName("依赖注入：doIoc()")
    @Test
    public void test() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        Assertions.assertEquals(false, beanContainer.isLoaded());
        beanContainer.loadBeans("cn.rwj.fakeprj");
        Assertions.assertEquals(true, beanContainer.isLoaded());
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assertions.assertEquals(true, mainPageController instanceof MainPageController);
        Assertions.assertEquals(null, mainPageController.getHeadLineShopCategoryCombineService());
        new DependencyInjector().doIoc();
        Assertions.assertNotEquals(null, mainPageController.getHeadLineShopCategoryCombineService());
    }

}
