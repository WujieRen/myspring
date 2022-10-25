package demo.generic;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 泛型接口，既可以被普通类实现，也可以被泛型类实现
 */
public interface GenericIFactory<T, N> {
    T nextObject();
    N nextNumber();
}
