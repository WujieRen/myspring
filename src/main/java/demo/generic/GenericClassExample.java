package demo.generic;

import lombok.Data;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 */
@Data
public class GenericClassExample<T> {

    private T member;

    public GenericClassExample(T member) {
        this.member = member;
    }

    /**
     * 泛型类的普通成员方法
     * @param target
     * @return
     */
    public T handleSomething(T target){
        return target;
    }

    public String sayHello(String name){
        return "Hello " + name;
    }

    /**
     * 泛型方法，
     *  1. 既能用在泛型类、泛型接口中，也可以用在普通类或者普通接口里
     *  2. 泛型方法的泛型类型不受制于泛型类，可以与泛型类不一致；
     *      即使将泛型形参改为和泛型类泛型形参一致，也可以传入和反省类型不同类型的实参
     *       如： GenericDemo -> stringExample.printArray(integers);     // 这里strinExample的形参是String(T)，但是也可以传入Integer(E，即和类形参不同的实参)实参
     * @param inputArray
     * @param <E>   泛型标示符，只有带有泛型标示符的方法才是泛型方法
     */
    public <E> void printArray(E[] inputArray) {
        for(E e : inputArray) {
            System.out.printf("%s ", e);
        }
        System.out.println();
    }

}
