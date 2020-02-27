package DomFaryna.FiveGuysOneRobot;

import DomFaryna.FiveGuysOneRobot.Controls.Motors;
import DomFaryna.FiveGuysOneRobot.Controls.StartButton;
import DomFaryna.FiveGuysOneRobot.Sensors.ImuJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.RangeFinderJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.SideFinderJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.XYZ;
import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Gpio;

import java.io.IOException;

public class main {
    private static RangeFinderJNI front;
    private static SideFinderJNI side;
    private static ImuJNI bno;
    private static StartButton start;
    private static Motors left;
    private static Motors right;

    private static void constructionCheck() throws InterruptedException {
        start.waitForPress();
        double distance = front.getDistanceIn();
        while(distance < 10.0){
            System.out.println(String.format("Distance: %.2f", distance));
            right.setSpeed(0.5);
            left.setSpeed(0.5);
            distance = front.getDistanceIn();
        }
        right.stop();
        left.stop();
        Thread.sleep(200);
        XYZ rotation = bno.getGyro();
        while(bno.getGyro().x < 85){
            right.setSpeed(-0.5);
            left.setSpeed(0.5);
        }
        right.stop();
        left.stop();
        Thread.sleep(200);
        while(bno.getGyro().x > -175){
            right.setSpeed(0.5);
            left.setSpeed(-0.5);
        }
        right.stop();
        left.stop();
        Thread.sleep(200);
    }

    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {
        Gpio.wiringPiSetupGpio();
        RaspiGpioProvider provider = new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING);
        GpioFactory.setDefaultProvider(provider);
        front = new RangeFinderJNI(1);
        //side = new SideFinderJNI(3);
        bno = new ImuJNI();
        start = new StartButton();
        left = new Motors(false);
        right = new Motors(true);
        //GpioPinDigitalOutput thisMF = gpio.provisionDigitalOutputPin(RaspiBcmPin.GPIO_21);
        //GpioPinDigitalOutput thisMF = gpio.provisionDigitalOutputPin(provider, RaspiBcmPin.GPIO_21);
        //GpioPinDigitalMultipurpose thisMF = gpio.provisionDigitalMultipurposePin(RaspiBcmPin.GPIO_21, PinMode.DIGITAL_OUTPUT);

        //constructionCheck();
        while(true){
            System.out.println(String.format("X: %.2f", bno.getGyro().x));
            System.out.println(String.format("Front: %.2f", front.getDistanceIn()));
            //System.out.println(String.format("Side: %.2f", side.getDistanceIn()));
            Thread.sleep(1000);
            //right.setSpeed(0.3);
            //left.setSpeed(0.3);
            //Thread.sleep(1000);
            //right.stop();
            //left.stop();
            //Thread.sleep(1000);
            //right.setSpeed(-0.3);
            //left.setSpeed(-0.3);
            //Thread.sleep(1000);
            //right.stop();
            //left.stop();
            //Thread.sleep(10000);
            //System.out.println(String.format("Front: %.2f", front.getDistanceIn()));
            //System.out.println(String.format("Side: %.2f", side.getDistanceIn()));
            //start.waitForPressAndPrint();
            Thread.sleep(500);
        }
    }
}
