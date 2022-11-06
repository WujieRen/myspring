package org.myspring.aopj.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.myspring.aop.aspect.DefaultAspect;
import org.myspring.aopj.PointcutLocator;

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
