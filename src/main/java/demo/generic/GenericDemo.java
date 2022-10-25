package demo.generic;

public class GenericDemo {
//    public static void handleMember(GenericClassExample<Integer> integerGenericClassExample) {
//    public static void handleMember(GenericClassExample<?> integerGenericClassExample) {    // 通配符会使得一些编译时该发现的错误无法发现
    public static void handleMember(GenericClassExample<? extends Number> integerGenericClassExample) {    // 通配符会使得一些编译时该发现的错误无法发现
        Integer result = 111 + (Integer) integerGenericClassExample.getMember();    //此时在传入String就可以在编译前发现错误
        System.out.println("result is " + result);
    }

    public static void main(String[] args) {
        GenericClassExample<String> stringExample = new GenericClassExample<String>("abc");
//        GenericClassExample<Integer> integerExample = new GenericClassExample<Integer>(123);
        GenericClassExample<Number> integerExample = new GenericClassExample<Number>(123);
        // 泛型擦除，通过以下打印或者反编译查看class文件结果，可以看到字节码文件中是没有泛型的
        System.out.println(stringExample.getClass());
        System.out.println(integerExample.getClass());

        handleMember(integerExample);
//        handleMember(stringExample);    //通配符会使得一些编译时该发现的错误无法发现

        Integer[] integers = {1, 2, 3, 4, 5, 6};
        Double[] doubles = {1.1, 1.2, 1.3, 1.4, 1.5};
        Character[] characters = {'A', 'B', 'C'};
        stringExample.printArray(integers);     // 这里strinExample的形参是String(T)，但是也可以传入Integer(E，即和类形参不同的实参)实参
        stringExample.printArray(doubles);
        stringExample.printArray(characters);
    }
}
