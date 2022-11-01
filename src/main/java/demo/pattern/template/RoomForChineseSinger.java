package demo.pattern.template;

/**
 * @Author rwj
 * @Date 2022/11/2
 * @Description
 */
public class RoomForChineseSinger extends KTVRoom{
    @Override
    protected void orderSong() {
        System.out.println("来首劲爆的中文歌。。");
    }

    @Override
    protected void orderExtra() {
        System.out.println("东西真便宜，一样来一份");
    }
}
