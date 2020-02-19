package DomFaryna.FiveGuysOneRobot.Sensors;

public class RangeFinderJNI {
    static {
        System.loadLibrary("rangeFinder");
        System.out.println("Successfully loaded rangeFinder library");
    }
    public RangeFinderJNI(){
        init();
    }

    // calibrates the rangeFinder. Must be called on startup
    public native void init();
    // gets the current distance, in mm
    public native int getDistance();
}
