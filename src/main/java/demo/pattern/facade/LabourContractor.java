package demo.pattern.facade;

import demo.pattern.facade.subclass.BrickLayer;
import demo.pattern.facade.subclass.BrickWorker;
import demo.pattern.facade.subclass.Mason;

/**
 * @Author rwj
 * @Date 2022/10/26
 * @Description 门面类，包工头，负责调度工人干活
 *
 */
public class LabourContractor {

    private Mason worker1 = new Mason();
    private BrickWorker worker2 = new BrickWorker();
    private BrickLayer worker3 = new BrickLayer();
    public void buildHouse() {
        worker1.mix();
        worker2.carry();
        worker3.neat();
    }
}
