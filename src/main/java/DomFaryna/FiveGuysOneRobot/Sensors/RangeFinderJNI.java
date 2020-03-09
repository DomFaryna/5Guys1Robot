package DomFaryna.FiveGuysOneRobot.Sensors;

import java.util.LinkedList;
import java.util.List;

public class RangeFinderJNI {
    private double avg = 0;
    private boolean done = false;
    private double prev = 0;
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
        double val = getDistance() / 25.4;
        if(val > prev + 50){
            return getDistanceIn();
        }
        prev = val;
        return prev;
    }

    public void resetAvg(){
        avg = 0;
        done = false;
    }

    public double getDistanceInAvg(){
        if(!done){
            for(int i = 0; i < 10; i++){
                avg = 0.9 * avg + (0.1 * getDistanceIn());
            }
            done = true;
        }
        avg = 0.9 * avg + (0.1 * getDistanceIn());
        return avg;
    }
}
