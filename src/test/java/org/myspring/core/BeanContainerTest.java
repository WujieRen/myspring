package org.myspring.core;

import org.myspring.mvc.DipatcherServlet;
import cn.rwj.fakeprj.controller.frontend.MainPageController;
import cn.rwj.fakeprj.service.solo.HeadLineService;
import cn.rwj.fakeprj.service.solo.impl.HeadLineServiceImpl;
import org.junit.jupiter.api.*;
import org.myspring.core.annotation.Controller;

/**
 * @Author rwj
 * @Date 2022/10/29
 * @Description
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {

    private static BeanContainer beanContainer;

    @BeforeAll
    static void init(){
        beanContainer = BeanContainer.getInstance();
    }

    @Order(1)
    @DisplayName("加载目标类及其实例到BeanContainer：loadBeansTest")
    @Test
    public void loadBeansTest(){
        Assertions.assertEquals(false, beanContainer.isLoaded());
        beanContainer.loadBeans("cn.rwj.fakeprj");
        Assertions.assertEquals(6, beanContainer.size());
        Assertions.assertEquals(true, beanContainer.isLoaded());
    }

    @DisplayName("根据类获取其实例：getBeanTest")
    @Order(2)
    @Test
    public void getBeanTest(){
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        Assertions.assertEquals(true, mainPageController instanceof MainPageController);
        DipatcherServlet dipatcherServlet = (DipatcherServlet) beanContainer.getBean(DipatcherServlet.class);
        Assertions.assertEquals(false, dipatcherServlet instanceof DipatcherServlet);
    }

    @DisplayName("根据注解获取对应的实例：getClassesByAnnotationTest")
    @Order(3)
    @Test
    public void getClassesByAnnotationTest(){
        Assertions.assertEquals(true, beanContainer.isLoaded());
        Assertions.assertEquals(3, beanContainer.getClassesByAnnotation(Controller.class).size());
    }

    @DisplayName("根据接口获取实现类：getClassesBySuperTest")
    @Order(4)
    @Test
    public void getClassesBySuperTest(){
        Assertions.assertEquals(true, beanContainer.getClassesBySuper(HeadLineService.class).contains(HeadLineServiceImpl.class));
    }

}
