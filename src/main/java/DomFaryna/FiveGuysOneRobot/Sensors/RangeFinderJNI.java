package DomFaryna.FiveGuysOneRobot.Sensors;

public class RangeFinderJNI {
    static {
        System.loadLibrary("rangeFinder");
        System.out.println("Successfully loaded rangeFinder library");
    }

    public RangeFinderJNI(int i2cBus) {
        init(i2cBus);
    }

    // calibrates the rangeFinder. Must be called on startup
    public native void init(int i2cBus);
    // gets the current distance, in mm
    public native int getDistance();
    public double getDistanceIn(){
        return getDistance() / 25.4;
    }
}
