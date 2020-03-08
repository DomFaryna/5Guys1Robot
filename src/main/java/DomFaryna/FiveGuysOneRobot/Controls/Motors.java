package DomFaryna.FiveGuysOneRobot.Controls;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

import java.util.concurrent.Semaphore;

// Turns out the motors just use PWM on certain GPIO ports. Sweet and easy
public class Motors {
    private enum Direction {
        FORWARD,
        BACKWARD,
        STOP,
        SHUTDOWN
    }
    // well, the wiring is detecting by the board, not the
    // pins, so I think its fine to leave it here

    private final Pin M3 = RaspiBcmPin.GPIO_06;
    int lol = 6;
    //int lol = 22;
    private final Pin M4 = RaspiBcmPin.GPIO_13;
    private final Pin PWMB = RaspiBcmPin.GPIO_12;

    private final Pin M1 = RaspiBcmPin.GPIO_20;
    private final Pin M2 = RaspiBcmPin.GPIO_21;
    int he = 21;
    private final Pin PWMA = RaspiBcmPin.GPIO_26;

    private GpioPinDigitalOutput d1;
    private GpioPinDigitalOutput d2;
    private GpioPinPwmOutput pwm;
    private Direction dir = Direction.STOP;
    private int multiplyFactor;
    private Semaphore lock;

    public Motors(boolean isRight) {
        GpioController gpio = GpioFactory.getInstance();
        if(isRight){
            this.d1 = gpio.provisionDigitalOutputPin(M1);
            this.d2 = gpio.provisionDigitalOutputPin(M2);
            //d2 = he;
            this.pwm = gpio.provisionSoftPwmOutputPin(PWMA);
            multiplyFactor = 100;
        } else {
            this.d1 = gpio.provisionDigitalOutputPin(M4);
            this.d2 = gpio.provisionDigitalOutputPin(M3);
            //d2 = lol;
            //this.pwm = gpio.provisionSoftPwmOutputPin(PWMA);
            this.pwm = gpio.provisionPwmOutputPin(PWMB);
            multiplyFactor = 1024;
        }
        d1.setShutdownOptions(true, PinState.LOW);
        d1.low();
        d2.setShutdownOptions(true, PinState.LOW);
        d2.low();

        System.out.println("Before\n");
        pwm.getProperties().forEach((x, y) -> System.out.println(String.format("Key: %s, Value: %s", x, y)));
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        Gpio.pwmSetClock(500);
        System.out.println("After\n");
        pwm.getProperties().forEach((x, y) -> System.out.println(String.format("Key: %s, Value: %s", x, y)));
        pwm.setPwm(0);
        lock = new Semaphore(1);
    }

    public void setSpeed(double speed){
        // when the motors are shutdown, fuck everyone else
        try {
            lock.acquire();

            if (dir == Direction.SHUTDOWN) {
                return;
            }
            if (dir != Direction.FORWARD && speed < 0.0) {
                dir = Direction.FORWARD;
                d1.low();
                d2.high();
            } else if (dir != Direction.BACKWARD && speed > 0.0) {
                dir = Direction.BACKWARD;
                d1.high();
                d2.low();
            }
            if (Math.abs(speed) > 0.85) {
                // set the max speed, and limit it
                speed = 0.85 * (speed) / Math.abs(speed);
            }
            pwm.setPwm((int) (multiplyFactor * Math.abs(speed)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.release();
        }
    }

    public void stop(){
        setSpeed(0);
        try {
            lock.acquire();
            d1.low();
            d2.low();
            dir = Direction.STOP;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.release();
        }
    }

    public void shutdown() {
        setSpeed(0);
        try {
            lock.acquire();
            d1.low();
            d2.low();
            dir = Direction.SHUTDOWN;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.release();
        }
    }
}
