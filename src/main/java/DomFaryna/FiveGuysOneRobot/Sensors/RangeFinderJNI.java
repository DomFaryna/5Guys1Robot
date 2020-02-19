package DomFaryna.FiveGuysOneRobot.Sensors;

public class RangeFinderJNI {
    public native void print();

    static {
        System.loadLibrary("hello");
    }
}
