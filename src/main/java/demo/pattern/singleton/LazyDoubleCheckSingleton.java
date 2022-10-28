package demo.pattern.singleton;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description 工厂模式 ——  懒汉模式
 *
 *  基于锁和双重检查机制的懒汉模式，且全局属性是 volatile 的， 才能实现线程安全
 */
public class LazyDoubleCheckSingleton {

    private static volatile LazyDoubleCheckSingleton instance;

    private LazyDoubleCheckSingleton() {}

    public static LazyDoubleCheckSingleton getInstance() {
        if(instance == null) {
            synchronized (LazyDoubleCheckSingleton.class) { // 该Class对象是全局唯一的，所有线程共享一个
                if(instance == null) {
                    /**
                     * 为啥要用 colatile 修饰 instance 属性？
                     *      因为创建对象这个动作在底层是分了三部来完成的
                     *      如果不用 volatile 修饰，那么第2部和第3部并不存在依赖关系，他们的顺序是不确定的
                     *      所以如果不用 volatile 修饰，那么就可能出现 instance != null 但是其实还没初始化好时（耗时较长）
                     *      某个线程赶紧来方法就发现 instance != null 从而直接返回还没初始化完整的 instance
                     */
                    //memory = allocate(); //1.分配对象内存空间
                    //instance(memory);    //2.初始化对象
                    //instance = memory;   //3.设置instance指向刚分配的内存地址，此时instance！=null
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

}
