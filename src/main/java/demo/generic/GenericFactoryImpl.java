package demo.generic;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 实现泛型接口的泛型类的泛型形参 必须与 泛型接口的泛型形参 一致（顺序可以不一致）
 */
//public class GenericFactoryImpl<A,B> implements GenericIFactory<T,N> {
public class GenericFactoryImpl<T, N> implements GenericIFactory<T,N> {

    @Override
    public T nextObject() {
        return null;
    }

    @Override
    public N nextNumber() {
        return null;
    }

}
