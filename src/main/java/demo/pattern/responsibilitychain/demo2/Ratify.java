package demo.pattern.responsibilitychain.demo2;

/**
 * @Author rwj
 * @Date 2022/11/3
 * @Description
 */
public interface Ratify {
    // 处理请求
    Result deal(Chain chain);

    /**
     * 接口描述：对request和Result封装，用来转发
     */
    interface Chain {
        // 获取当前request
        Request request();

        // 转发request
        Result proceed(Request request);
    }

}
