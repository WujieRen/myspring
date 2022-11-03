package demo.pattern.responsibilitychain.demo3;

/**
 * @Author rwj
 * @Date 2022/11/4
 * @Description 责任链模式的本质：
 *  1. 有一个接口/父抽象类，规定子类必须要实现的方法
 *  2. 多个实现了接口/父抽象类的子类，每个子类代表不同的策略
 *  3. 有某种机制能够把所有策略串在一条链上：
 *      1. 可以是持有另一种策略的引用（demo1）
 *      2. 可以通过一个策略集合，把所有策略添加进来，并暴露添加策略的接口（demo2 & demo3）
 *          1. 通过for循环直接遍历所有策略（demo3）
 *          2. 通过构造策略类的实例，调用接口/父抽象类的某个方法A，然后在该方法中调用策略的处理逻辑方法，
 *          在每个策略处理逻辑方法中，通过继续调用接口/父抽象类的A，循环调用，完成对策略的递归/遍历
 *  4. 有机制能够推进/递归链上的策略，然后根据处理结果做出不同的行为（如找到合适策略就结束递归策略，或者遍历完了也没找到就做出不同响应）
 *
 *  有点像是把策略模式串起来，并自动进行递归/遍历策略。
 *  策略模式关注点在策略，将不同策略传到构造方法中以调用不同子类（策略类）的处理逻辑。
 *  而责任链模式的重点在链的自动递归/遍历。
 */
public class Main {
    public static void main(String[] args) {
        Request request = new Request();
        request.str = "恭喜任帅哥早起。";
        Response response = new Response();
        response.str = "response";

        FilterChain chain = new FilterChain();
//        chain.add(new HTMLFilter()).add(new SensitiveFilter());
        chain.doFilter(request, response);
        System.out.println(request.str);
        System.out.println(response.str);
    }
}
