package org.myspring.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description
 */
@AllArgsConstructor
@Getter
public class AspectInfo {
    private int order;
    private DefaultAspect aspect;
}
