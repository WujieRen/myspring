package org.myspring.mvc;

/**
 * @Author rwj
 * @Date 2022/11/9
 * @Description 请求处理器责任链，
 *
 *  1.以责任链模式处理请求
 *      1.遍历注册的请求处理器列表
 *        1.直到某个请求处理器返回 false 为止
 *      2.期间如果出现异常，则交由内部错误渲染器处理
 *  2.委派给特定的渲染（Render）实例对处理后的结果进行渲染
 *      1.如果请求处理器实现类均未选择合适的渲染器，则使用默认的
 *      2.调用渲染器的render方法对结果进行渲染
 *
 */
public class RequestProcessorChain {

}
