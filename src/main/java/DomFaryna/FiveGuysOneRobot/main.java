package DomFaryna.FiveGuysOneRobot;

import DomFaryna.FiveGuysOneRobot.Sensors.ImuJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.RangeFinderJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.XYZ;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {
        String javaLibPath = System.getProperty("java.library.path");
        System.out.println(javaLibPath);
        RangeFinderJNI he = new RangeFinderJNI();
        ImuJNI ha = new ImuJNI();
        while (true) {
            XYZ location = ha.getGyro();
            XYZ acc = ha.getAcc();
            XYZ mag = ha.getMag();
            //System.out.println(String.format("gyroStuff X: %f, Y: %f, Z: %f", location.x, location.y, location.z));
            System.out.println(String.format("magStuff X: %f, Y: %f, Z: %f", mag.x, mag.y, mag.z));
            //System.out.println(String.format("accStuff X: %f, Y: %f, Z: %f", acc.x, acc.y, acc.z));
            Thread.sleep(1000);
        }
        //while (true) {
        //    System.out.println(String.format("Distance %d", he.getDistance()));
        //    Thread.sleep(1000);
        //}
    }
}
