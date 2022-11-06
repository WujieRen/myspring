package org.myspring.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

/**
 * @Author rwj
 * @Date 2022/11/6
 * @Description 解析Aspect表达式并且定位被织入的目标
 */
public class PointcutLocator {

    /**
     * Pointcut解析器，为简单直接支持 Aspectj 的所有表达式，以便支持对众多表达式的解析
     */
    private PointcutParser pointcutParser =
            PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();

    /**
     * 表达式解析器
     */
    private PointcutExpression pointcutExpression;
    public PointcutLocator(String pointcutExpression) {
        this.pointcutExpression = pointcutParser.parsePointcutExpression(pointcutExpression);
    }

    /**
     * 判断传入的Class对象是否是 @AspectJ 的目标代理类，即匹配 Pointcut 表达式(初筛)
     *
     * @param targetClass 目标类
     * @return 是否匹配
     */
    public boolean roughMatches(Class<?> targetClass) {
        boolean res = pointcutExpression.couldMatchJoinPointsInType(targetClass);
        return res;
    }

    /**
     * 判断传入的Method对象是否是 @AspectJ 的目标代理方法，即匹配 Pointcut 表达式(精筛)
     * @param method
     * @return
     */
    public boolean accurateMatches(Method method){
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
        if(shadowMatch.alwaysMatches()) {
            return true;
        } else {
            return false;
        }
//        return shadowMatch.alwaysMatches();
    }

}
