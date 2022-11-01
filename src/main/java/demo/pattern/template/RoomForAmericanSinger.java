package demo.pattern.template;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description
 */
public class RoomForAmericanSinger extends KTVRoom{
    @Override
    protected void orderSong() {
        System.out.println("Chinese song in English version please...");
    }
}
