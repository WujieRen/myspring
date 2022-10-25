package demo.generic;

import java.util.Random;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 由于<T, N> 指定成了 <String, Integer>。所以自动生成了返回值类型分别为 String(T) 和 Integer(N) 的方法
 */
public class RobotFactory implements GenericIFactory<String, Integer> {

    private String[] stringRobot = new String[]{"Hello","Hi"};
    private Integer[] integerRobot = new Integer[]{111,0};

    @Override
    public String nextObject() {
        Random random = new Random();
        return stringRobot[random.nextInt(2)];//[0,2)
    }

    @Override
    public Integer nextNumber() {
        Random random = new Random();
        return integerRobot[random.nextInt(2)];
    }

    public static void main(String[] args) {
        GenericIFactory<String, Integer> factory = new RobotFactory();
        System.out.println(factory.nextObject());
        System.out.println(factory.nextNumber());
    }

}
