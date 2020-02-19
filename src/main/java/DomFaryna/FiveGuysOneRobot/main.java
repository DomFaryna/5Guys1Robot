package DomFaryna.FiveGuysOneRobot;

import DomFaryna.FiveGuysOneRobot.Sensors.RangeFinderJNI;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException {
        RangeFinderJNI he = new RangeFinderJNI();
        he.print();

//        Consts con = Consts.getInstance();
//        Console console = new Console();
//        console.promptForExit();
//        System.out.println(String.format("BUS port %s", con.getBUS()));
//        I2CBus i2c = I2CFactory.getInstance(con.getBUS());
//        I2CDevice rangeFinder = i2c.getDevice(con.getRANGE_FINDER_ID());
//        int blah = rangeFinder.read(0xC1);
//        System.out.println(Integer.toHexString(blah));

//        blah = rangeFinder.read(0xC0);
//        System.out.println(Integer.toHexString(blah));
//        blah = rangeFinder.read(0xC2);
//        System.out.println(Integer.toHexString(blah));
//        blah = rangeFinder.read(0x51);
//        System.out.println(Integer.toHexString(blah));
//        blah = rangeFinder.read();
//        System.out.println(Integer.toHexString(blah));
    }
}
