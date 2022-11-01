package demo.pattern.template;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description
 */
public class TemplateDemo {
    public static void main(String[] args) {
        System.out.println("---------   富有的中国人   ---------");
        KTVRoom room1 = new RoomForChineseSinger();
        room1.procedure();
        System.out.println("---------   贫穷的老外   ---------");
        KTVRoom room2 = new RoomForAmericanSinger();
        room2.procedure();
    }
}
