package DomFaryna.FiveGuysOneRobot.Sensors;

public class ImuJNI {
    private XYZ<Double> offset = new XYZ<>(0.0, 0.0, 0.0);
    private double xOffset;
    private double yOffset;
    private double zOffset;
    static {
        System.loadLibrary("imu");
        System.out.println("Successfully loaded the imu library");
    }

    public ImuJNI(){
        calibrate();
        XYZ<Double> calibration = new XYZ<>(0.0, 0.0, 0.0);
        System.out.println("Calibrating IMU");
        for (int i = 0; i < 2000; i++) {
            XYZ<Double> val = getGyro();
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
    }

    public native void calibrate();
    // Note, for each of these function, 0 is x, 1 is y, 2 is z.
    private native double[] getRawGyro();
    private native double[] getRawAcc();
    private native double[] getRawMag();

    public XYZ<Double> getGyro(){
        double[] raw = getRawGyro();
        System.out.println(String.format("Offsets %f %f %f", offset.x, offset.y, offset.z));
        return new XYZ<Double>(raw[0] - offset.x, raw[1] - offset.y, raw[2] - offset.z);
    }

    //public XYZ<Double> getAcc(){
    //    double[] raw = getRawAcc();
    //    return new XYZ<Double>(raw[0], raw[1], raw[2]);
    //}

    //public XYZ<Double> getMag(){
    //    double[] raw = getRawMag();
    //    return new XYZ<Double>(raw[0], raw[1], raw[2]);
    //}
}
