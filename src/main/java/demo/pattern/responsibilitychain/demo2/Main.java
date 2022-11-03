package demo.pattern.responsibilitychain.demo2;

/**
 * @Author rwj
 * @Date 2022/11/3
 * @Description 责任链模式
 *
 *      参考：
 *          https://blog.csdn.net/u012810020/article/details/71194853
 *          https://blog.csdn.net/weixin_48052161/article/details/118230819
 *
 *
 */
public class Main {
    public static void main(String[] args) {
        Request request = new Request.Builder().setName("张三").setDays(7).setReason("事假").build();
        ChainOfResponsibilityClient client = new ChainOfResponsibilityClient();
        Result result = client.execute(request);

        System.out.println(result);
    }
}
