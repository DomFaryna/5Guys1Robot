package DomFaryna.FiveGuysOneRobot.Sensors;

// SideFinder is the side range finder. Another i2c call using a native input.
public class SideFinderJNI {
    static {
        System.loadLibrary("sideFinder");
        System.out.println("Successfully loaded sideFinder library");
    }
    public SideFinderJNI(int i2cBus){
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
