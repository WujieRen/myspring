package demo.pattern.facade;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description
 *  门面模式：子系统的外部与其内部的通信必须通过统一的对象进行。提供一个高层次的接口，使得子系统更易于使用
 */
public class Client {
    public static void main(String[] args) {
        LabourContractor baogongtou = new LabourContractor();
        baogongtou.buildHouse();
    }
}
