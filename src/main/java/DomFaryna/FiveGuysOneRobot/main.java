package DomFaryna.FiveGuysOneRobot;

import DomFaryna.FiveGuysOneRobot.Sensors.RangeFinderJNI;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException {
        String javaLibPath = System.getProperty("java.library.path");
        System.out.println(javaLibPath);
        RangeFinderJNI he = new RangeFinderJNI();
    }
}
