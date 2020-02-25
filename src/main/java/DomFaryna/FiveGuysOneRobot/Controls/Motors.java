package DomFaryna.FiveGuysOneRobot.Controls;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.Gpio;

// Turns out the motors just use PWM on certain GPIO ports. Sweet and easy
public class Motors {
    // well, the wiring is detecting by the board, not the
    // pins, so I think its fine to leave it here

    //private final Pin M3 = RaspiPin.GPIO_06;
    //private final Pin M4 = RaspiPin.GPIO_13;
    //private final Pin PWMB = RaspiPin.GPIO_12;

    //private final Pin M1 = RaspiPin.GPIO_20;
    //private final Pin M2 = RaspiPin.GPIO_21;
    //private final Pin PWMA = RaspiPin.GPIO_26;

    private final Pin M3 = RaspiBcmPin.GPIO_06;
    private final Pin M4 = RaspiBcmPin.GPIO_13;
    private final Pin PWMB = RaspiBcmPin.GPIO_12;

    private final Pin M1 = RaspiBcmPin.GPIO_20;
    private final Pin M2 = RaspiBcmPin.GPIO_21;
    private final Pin PWMA = RaspiBcmPin.GPIO_26;
    // Maybe?

    private GpioPinDigitalOutput d1;
    private GpioPinDigitalOutput d2;
    private GpioPinPwmOutput pwm;
    private boolean forward = true;
    private int multiplyFactor;

    public Motors(boolean isRight) {
        GpioController gpio = GpioFactory.getInstance();
        if(isRight){
            this.d1 = gpio.provisionDigitalOutputPin(M1);
            this.d2 = gpio.provisionDigitalOutputPin(M2);
            //gpio.provisionDigitalOutputPin(PWMA);
            this.pwm = gpio.provisionSoftPwmOutputPin(PWMA);
            multiplyFactor = 100;
        } else {
            this.d1 = gpio.provisionDigitalOutputPin(M3);
            this.d2 = gpio.provisionDigitalOutputPin(M4);
            this.pwm = gpio.provisionPwmOutputPin(PWMB);
            multiplyFactor = 1024;
            //gpio.provisionDigitalOutputPin(PWMB);
        }
        d1.setPullResistance(PinPullResistance.OFF);
        d2.setPullResistance(PinPullResistance.OFF);
        d1.high();
        d2.low();

        System.out.println("Before\n");
        pwm.getProperties().forEach((x, y) -> System.out.println(String.format("Key: %s, Value: %s", x, y)));
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);
        //Gpio.pwmSetRange(100);
        Gpio.pwmSetClock(500);
        System.out.println("After\n");
        pwm.getProperties().forEach((x, y) -> System.out.println(String.format("Key: %s, Value: %s", x, y)));
        pwm.setPwm(0);
    }

    public void setSpeed(double speed){
        if(forward && speed < 0.0){
            forward = false;
            d1.low();
            d2.high();
        } else if(!forward && speed > 0.0){
            forward = true;
            d1.high();
            d2.low();
        }
        pwm.setPwm((int) (multiplyFactor * Math.abs(speed)));
    }

    public void stop(){
        setSpeed(0);
    }
}
