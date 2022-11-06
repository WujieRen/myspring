package org.myspring.aop.aspectj.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myspring.aop.native_.aspect.DefaultAspect;
import org.myspring.aop.aspectj.PointcutLocator;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description
 */
@AllArgsConstructor
@Getter
public class AspectJInfo {

    private int order;
    private DefaultAspect aspect;
    private PointcutLocator pointcutLocator;

}
