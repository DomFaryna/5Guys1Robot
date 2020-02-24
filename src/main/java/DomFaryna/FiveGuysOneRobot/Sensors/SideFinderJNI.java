package DomFaryna.FiveGuysOneRobot.Sensors;

// SideFinder is the side range finder. Another i2c call using a native input.
public class SideFinderJNI {
    static {
        System.loadLibrary("sideFinder");
        System.out.println("Successfully loaded sideFinder library");
    }
    public SideFinderJNI(){
        init();
    }

    // calibrates the rangeFinder. Must be called on startup
    public native void init();
    // gets the current distance, in mm
    public native int getDistance();
}
