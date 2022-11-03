package demo.pattern.responsibilitychain.demo2;

import java.util.List;

/**
 * @Author rwj
 * @Date 2022/11/3
 * @Description
 */
public class RealChain implements Ratify.Chain {

    public Request request;
    public List<Ratify> ratifyList;
    public int index;

    /**
     * @param ratifyList Ratify接口的实现类集合
     * @param request    具体的请求Request实例
     * @param index      已经处理过该request的责任人数量
     */
    public RealChain(List<Ratify> ratifyList, Request request, int index) {
        this.ratifyList = ratifyList;
        this.request = request;
        this.index = index;
    }


    @Override
    public Request request() {
        return request;
    }

    /**
     * 具体转发功能
     *
     * @return
     */
    @Override
    public Result proceed(Request request) {
        Result proceed = null;
        if (ratifyList.size() > index) {
            RealChain chain = new RealChain(ratifyList, request, index + 1);
            Ratify ratify = ratifyList.get(index);
            proceed = ratify.deal(chain);
        }
        return proceed;
    }
}
