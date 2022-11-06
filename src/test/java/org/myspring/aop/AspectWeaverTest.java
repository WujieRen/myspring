package org.myspring.aop;

import cn.rwj.fakeprj.controller.frontend.MainPageController;
import cn.rwj.fakeprj.controller.superadmin.HeadLineOperationController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.myspring.aopj.AspectJWeaver;
import org.myspring.core.BeanContainer;
import org.myspring.inject.DependencyInjector;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description
 */
public class AspectWeaverTest {

    @DisplayName("织入通用逻辑测试：doAop")
    @Test
    public void doAopTest() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("cn.rwj.fakeprj");
//        new AspectWeaver(beanContainer).doAOP();
        new AspectJWeaver(beanContainer).doAOP();
        new DependencyInjector().doIoc();
//        HeadLineOperationController headLineOperationController = (HeadLineOperationController)beanContainer.getBean(HeadLineOperationController.class);
//        headLineOperationController.addHeadLine(null, null);

        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        mainPageController.getMainPageInfo(null, null);
    }

}
