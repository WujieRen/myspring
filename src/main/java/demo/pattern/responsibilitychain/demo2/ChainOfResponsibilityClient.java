package demo.pattern.responsibilitychain.demo2;

import java.util.ArrayList;

/**
 * @Author rwj
 * @Date 2022/11/3
 * @Description
 */
public class ChainOfResponsibilityClient {

    private ArrayList<Ratify> ratifies;

    public ChainOfResponsibilityClient() {
        ratifies = new ArrayList<>();
        ratifies.add(new GroupLeader());
        ratifies.add(new Manager());
        ratifies.add(new DepartmentHeader());
    }

    /**
     * 方法描述：为了展示“责任链模式”的真正的迷人之处（可扩展性），在这里构造该方法以便添加自定义的“责任人”
     *
     * @param ratify  批准接口
     */
    public void addRatifys(Ratify ratify) {
        ratifies.add(ratify);
    }

    /**
     * 方法描述：执行请求
     *
     * @param request   请求
     * @return
     */
    public Result execute(Request request) {
//        ArrayList<Ratify> arrayList = new ArrayList<>();
//        arrayList.addAll(ratifies);
//        arrayList.add(new GroupLeader());
//        arrayList.add(new Manager());
//        arrayList.add(new DepartmentHeader());

        RealChain realChain = new RealChain(ratifies, request, 0);
        return realChain.proceed(request);
    }

}
