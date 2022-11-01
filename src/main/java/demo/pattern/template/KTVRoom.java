package demo.pattern.template;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description  模板方法模式：
 *      抽象类实现通用逻辑，定义公开的模板结构/公开定义执行它的方法、方式。子类可以按需重写方法实现，但调用将以抽象类中定义的方式进行。
 *      主要是定义一个操作骨架/框架，而将其部分非通用实现延迟到子类中。不改变整体骨架可以重新定义/修改该骨架下的某些步骤。
 *
 *      注意：为防止恶意操作，一般模板方法都加上 final 关键词。
 */
public abstract class KTVRoom {

    public final void procedure(){
        openDevice();
        orderSong();
        orderExtra();
        pay();
    }

    //模板自带方法，使用前必须得打开设备
    private void openDevice(){
        System.out.println("打开视频和音响");
    }

    //抽象方法，子类必须实现的方法，必须得选歌
    protected abstract void orderSong();

    //钩子方法，子类可以依据情况实现的方法
    protected void orderExtra(){}

    //模板自带方法，用后必须得付款
    private void pay(){
        System.out.println("支付本次的消费账单。。。");
    }

}
