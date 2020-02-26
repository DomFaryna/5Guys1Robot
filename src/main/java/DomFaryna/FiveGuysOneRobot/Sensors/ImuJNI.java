package DomFaryna.FiveGuysOneRobot.Sensors;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ImuJNI {
    private XYZ offset = new XYZ();
    private final XYZ current = new XYZ();
    private double xOffset;
    private double yOffset;
    private double zOffset;

    // Used to keep track of the gyro and integrate it
    private ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
    static {
        System.loadLibrary("bno");
        System.out.println("Successfully loaded the imu library");
    }

    public ImuJNI(){
        calibrate();
        XYZ calibration = new XYZ();
        System.out.println("Calibrating IMU");
        for (int i = 0; i < 2000; i++) {
            XYZ val = getGyro();
            val.round();
            calibration.x += val.x;
            calibration.y += val.y;
            calibration.z += val.z;
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("IMU calibration finished");
        calibration.x /= 2000;
        calibration.y /= 2000;
        calibration.z /= 2000;
        offset = calibration;
        final Runnable update = () -> updateGyro();
        exec.scheduleAtFixedRate(update, 10, 10, TimeUnit.MILLISECONDS);
    }

    public native void calibrate();
    // Note, for each of these function, 0 is x, 1 is y, 2 is z.
    private native double[] getRawGyro();
    private native double[] getRawAcc();
    private native double[] getRawMag();

    private void updateGyro() {
        double[] raw = getRawGyro();
        // Sample time is 10 ms, need to multiply the outcome by 0.01 to get actual result
        XYZ sol = new XYZ((raw[0] - offset.x), (raw[1] - offset.y), (raw[2] - offset.z));
        sol.round();
        sol.x *= 0.01;
        sol.y *= 0.01;
        sol.z *= 0.01;
        synchronized (current) {
            current.x += sol.x;
            current.y += sol.y;
            current.z += sol.z;
        }
    }

    public XYZ getGyro() {
        synchronized (current){
            return new XYZ(current.x, current.y, current.z);
        }
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
