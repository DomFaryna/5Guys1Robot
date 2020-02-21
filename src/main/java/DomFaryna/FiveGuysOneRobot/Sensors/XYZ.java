package DomFaryna.FiveGuysOneRobot.Sensors;

import lombok.Getter;

// XYZ is a helper class for the rangefinder, so that we can get the x y z values read from the gyro in a way
// Dom won't forget
public class XYZ {
    public double x;
    public double y;
    public double z;

    public XYZ(){
         new XYZ(0.0, 0.0, 0.0);
    }
    public XYZ(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // round x, y, and z to 2 decimal points
    public void round(){
        x = ((int)((x * 10) + 0.5))/10.0;
        y = ((int)((y * 10) + 0.5))/10.0;
        z = ((int)((z * 10) + 0.5))/10.0;
    }
}
