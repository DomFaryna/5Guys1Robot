package DomFaryna.FiveGuysOneRobot.Sensors;

public class RangeFinderJNI {
    // calibrates the rangeFinder. Must be called on startup
    public native void init();

    // gets the current distance, in mm
    public native int getDistance();

    static {
        System.loadLibrary("rangeFinder");
        System.out.println("Successfully loaded rangeFinder libary");
    }
}
