package demo.pattern.singleton;

/**
 * @Author rwj
 * @Date 2022/10/28
 * @Description 单例模式 —— 饿汉模式
 *  饿汉模式：
 *      类被加载时立即被初始化，并创建唯一实例
 *      所有类共享同一个实例，是线程安全的
 */
public class StarvingSingleton {
    private static final StarvingSingleton instance = new StarvingSingleton();

    private StarvingSingleton() {
    }

    public static StarvingSingleton getInstance() {
        return instance;
    }
}
