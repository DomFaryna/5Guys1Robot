package DomFaryna.FiveGuysOneRobot;

import DomFaryna.FiveGuysOneRobot.Controls.Motors;
import DomFaryna.FiveGuysOneRobot.Controls.StartButton;
import DomFaryna.FiveGuysOneRobot.Sensors.ImuJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.RangeFinderJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.SideFinderJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.XYZ;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPinNumberingScheme;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Gpio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class main {
    private static RangeFinderJNI front;
    private static SideFinderJNI side;
    private static ImuJNI bno;
    private static StartButton start;
    private static Motors left;
    private static Motors right;

    private static void constructionCheck() throws InterruptedException {
        System.out.println("waiting for button");
        start.waitForPress();
        System.out.println("Button has been pressed");
        double distance = front.getDistanceIn();
        while (distance > 15.0) {
            System.out.println(String.format("Distance: %.2f", distance));
            right.setSpeed(0.2);
            left.setSpeed(0.2);
            distance = front.getDistanceIn();
            Thread.sleep(20);
        }
        right.stop();
        left.stop();
        Thread.sleep(1000);
        XYZ rotation = bno.getGyro();
        while (rotation.x < 75) {
            System.out.println(String.format("rotation: %.2f", rotation.x));
            right.setSpeed(-0.3);
            left.setSpeed(0.3);
            rotation = bno.getGyro();
            Thread.sleep(20);
        }
        System.out.println("GOT TO DESTINATION");
        right.stop();
        left.stop();
        Thread.sleep(1000);
        rotation = bno.getGyro();
        while (rotation.x > -165) {
            System.out.println(String.format("rotation: %.2f", rotation.x));
            right.setSpeed(0.3);
            left.setSpeed(-0.3);
            rotation = bno.getGyro();
            Thread.sleep(20);
        }
        System.out.println(String.format("Final rotation: %.2f", rotation.x));
        right.stop();
        left.stop();
        System.out.println("IM DONEEEE");
        Thread.sleep(20);
    }

    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {
        Gpio.wiringPiSetupGpio();
        RaspiGpioProvider provider = new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING);
        GpioFactory.setDefaultProvider(provider);
        front = new RangeFinderJNI(1);
        side = new SideFinderJNI(3);
        bno = new ImuJNI();
        start = new StartButton();
        left = new Motors(false);
        right = new Motors(true);
        constructionCheck();
        while(true) {
            System.out.println(String.format("X: %.2f", bno.getGyro().x));
            System.out.println(String.format("Front: %.2f", front.getDistanceIn()));
            System.out.println(String.format("Side: %.2f", side.getDistanceIn()));
        }
    }
}
