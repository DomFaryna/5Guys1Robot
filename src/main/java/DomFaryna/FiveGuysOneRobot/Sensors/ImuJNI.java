package DomFaryna.FiveGuysOneRobot.Sensors;

public class ImuJNI {
    static {
        System.loadLibrary("imu");
        System.out.println("Successfully loaded the imu library");
    }
    public ImuJNI(){
        calibrate();
    }

    public native void calibrate();
    // Note, for each of these function, 0 is x, 1 is y, 2 is z.
    private native double[] getRawGyro();
    private native double[] getRawAcc();
    private native double[] getRawMag();

    public XYZ<Double> getGyro(){
        double[] raw = getRawGyro();
        return new XYZ<Double>(raw[0], raw[1], raw[2]);
    }

    public XYZ<Double> getAcc(){
        double[] raw = getRawAcc();
        return new XYZ<Double>(raw[0], raw[1], raw[2]);
    }

    public XYZ<Double> getMag(){
        double[] raw = getRawMag();
        return new XYZ<Double>(raw[0], raw[1], raw[2]);
    }
}
