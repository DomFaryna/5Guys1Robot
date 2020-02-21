package DomFaryna.FiveGuysOneRobot.Sensors;

import lombok.Getter;

// XYZ is a helper class for the rangefinder, so that we can get the x y z values read from the gyro in a way
// Dom won't forget
public class XYZ<T> {
    public T x;
    public T y;
    public T z;

    public XYZ(T x, T y, T z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
