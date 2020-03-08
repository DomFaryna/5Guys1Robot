package DomFaryna.FiveGuysOneRobot;

import DomFaryna.FiveGuysOneRobot.Controls.Motors;
import DomFaryna.FiveGuysOneRobot.Controls.PID;
import DomFaryna.FiveGuysOneRobot.Controls.StartButton;
import DomFaryna.FiveGuysOneRobot.Sensors.ImuJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.RangeFinderJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.SideFinderJNI;
import DomFaryna.FiveGuysOneRobot.Sensors.XYZ;
import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.Gpio;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class main {
    private static RangeFinderJNI front;
    private static SideFinderJNI side;
    private static ImuJNI bno;
    private static StartButton start;
    private static Motors left;
    private static Motors right;
    private static GpioController gpio;

    // when given ANY keyboard command, stop everything
    public static void stopListener() {
        try {
            System.in.read();
            left.shutdown();
            right.shutdown();
            gpio.shutdown();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        gpio = GpioFactory.getInstance();
        front = new RangeFinderJNI(1);
        side = new SideFinderJNI(3);
        bno = new ImuJNI();
        start = new StartButton();
        left = new Motors(false);
        right = new Motors(true);
        Thread ha = new Thread(main::stopListener);
        ha.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            gpio.shutdown();
            System.out.println("Shutting down");
        }));

        PID turnPID = new PID(0.0151, 0, 0.00135, 0.12);
        int cycles = 0;

        int i = 0;
        while(true) {
            i++;
            double angle = bno.getGyro().x;
            double power = turnPID.iterate(90, angle);
            System.out.println(String.format("X: %.2f, turnPower: %.2f", bno.getGyro().x, power));
            if(power > 0){
                right.setSpeed(0.85 * -power * 1.1);
                left.setSpeed(0.85 * power);
            } else {
                right.setSpeed(0.85 * -power);
                left.setSpeed(0.85 * power * 1.1);
            }


            if (angle > 88 && angle < 92) {
                cycles++;
            } else {
                cycles = 0;
            }

            if (cycles > 5) {
                System.out.println("All done!");
                System.out.println(String.format("Took %d iteration to get to 90", i));
                right.stop();
                left.stop();
                break;
            }
            Thread.sleep(20);

            ////System.out.println(String.format("Front: %.2f", front.getDistanceIn()));
            ////System.out.println(String.format("Side: %.2f", side.getDistanceIn()));
        }
    }
}
