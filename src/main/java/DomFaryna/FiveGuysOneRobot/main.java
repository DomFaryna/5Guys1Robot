package DomFaryna.FiveGuysOneRobot;

import DomFaryna.FiveGuysOneRobot.Controls.Motors;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Gpio;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {
        String javaLibPath = System.getProperty("java.library.path");
        System.out.println(javaLibPath);
        //int err = Gpio.wiringPiSetupSys();
        //if(err != 0){
        //    System.out.println("Fuck");
        //}
        //RangeFinderJNI he = new RangeFinderJNI();
        //SideFinderJNI side = new SideFinderJNI(1);
        //ImuJNI ha = new ImuJNI();
        Gpio.wiringPiSetupGpio();

        Motors right = new Motors(true);
        Motors left = new Motors(false);
        //SideFinder find = new SideFinder();
        while (true) {
            //right.setSpeed(-0.25);
            System.out.println("forward");
            right.setSpeed(0.15);
            left.setSpeed(0.15);
            Thread.sleep(1000);
            System.out.println("stop");
            right.stop();
            left.stop();
            Thread.sleep(1000);
            System.out.println("reverse");
            left.setSpeed(-0.15);
            right.setSpeed(-0.15);
            Thread.sleep(1000);
            System.out.println("stop");
            right.stop();
            left.stop();
            Thread.sleep(1000);

            //System.out.println(String.format("Distance from side %dmm", side.getDistance()));
            //System.out.println(String.format("distance %dmm", he.getDistance()));
            //XYZ location = ha.getGyro();
            //XYZ acc = ha.getAcc();
            //XYZ mag = ha.getMag();
            //System.out.println(String.format("gyroStuff X: %f, Y: %f, Z: %f", location.x, location.y, location.z));
            //System.out.println(String.format("magStuff X: %f, Y: %f, Z: %f", mag.x, mag.y, mag.z));
            //System.out.println(String.format("accStuff X: %f, Y: %f, Z: %f", acc.x, acc.y, acc.z));
            //System.out.println(String.format("range %d", find.getDistance()));
            //System.out.println("Motorss should be go go ");
        }
    }
}
