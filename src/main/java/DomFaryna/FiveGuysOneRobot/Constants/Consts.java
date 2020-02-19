package DomFaryna.FiveGuysOneRobot.Constants;

import com.pi4j.io.i2c.I2CBus;
import lombok.Getter;

public abstract class Consts {
    @Getter
    protected int BUS;

    @Getter
    protected int RANGE_FINDER_ID;

    // singleton stuff from here on in
    private static Consts con;
    public static Consts getInstance(){
        if (con == null){
            synchronized (Consts.class){
                // Logic to decided what pi version we are goes here. For now, just assume we are running with pi 2
                con = new ConstsPi2();
            }
        }
        return con;
    }

    private static class ConstsPi2 extends Consts {
        private ConstsPi2(){
            BUS = I2CBus.BUS_1;
            RANGE_FINDER_ID = 0x29;
        }
    }
}
