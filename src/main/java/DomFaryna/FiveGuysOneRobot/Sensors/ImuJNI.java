package DomFaryna.FiveGuysOneRobot.Sensors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ImuJNI {
    private int cycles = 0;
    private XYZ prev;

    // Used to keep track of the gyro and integrate it
    private ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
    static {
        System.loadLibrary("bno");
        System.out.println("Successfully loaded the bno library");
    }

    public ImuJNI(){
        calibrate();
        XYZ prev = new XYZ();
    }

    public native void calibrate();
    // Note, for each of these function, 0 is x, 1 is y, 2 is z.
    private native double[] getRawGyro();
    private native double[] getRawAcc();
    private native double[] getRawMag();

    public void resetCycles(){
        cycles = 0;
    }

    public XYZ getGyro() {
        double[] raw = getRawGyro();
        XYZ sol = new XYZ(raw[0], raw[1], raw[2]);
        sol.round();
        if(prev != null){
            double diff = sol.x - prev.x;
            if(diff > 90){
                cycles--;
            } else if(diff < -90){
                cycles++;
            }
        }
        prev = new XYZ(sol.x, sol.y, sol.z);
        sol.x += (cycles * 360);
        return sol;
    }

    public XYZ getAcc() {
        double[] raw = getRawAcc();
        XYZ sol = new XYZ(raw[0], raw[1], raw[2]);
        sol.round();
        return sol;
    }

    public XYZ getMag(){
        double[] raw = getRawMag();
        return new XYZ(raw[0], raw[1], raw[2]);
    }
}
