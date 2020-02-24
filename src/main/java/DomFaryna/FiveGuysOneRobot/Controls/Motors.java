package DomFaryna.FiveGuysOneRobot.Controls;

import com.pi4j.io.gpio.*;

// Turns out the motors just use PWM on certain GPIO ports. Sweet and easy
public class Motors {
    // well, the wiring is detecting by the board, not the
    // pins, so I think its fine to leave it here
    private final Pin M3 = RaspiPin.GPIO_06;
    private final Pin M4 = RaspiPin.GPIO_13;
    private final Pin M1 = RaspiPin.GPIO_20;
    private final Pin M2 = RaspiPin.GPIO_21;
    private final Pin PWMA = RaspiPin.GPIO_26;
    private final Pin PWMB = RaspiPin.GPIO_12;

    private GpioPinDigitalOutput d1;
    private GpioPinDigitalOutput d2;
    private GpioPinPwmOutput pwm;
    private boolean forward = true;

    public Motors(boolean isRight) {
        GpioController gpio = GpioFactory.getInstance();
        if(isRight){
            this.d1 = gpio.provisionDigitalOutputPin(M1);
            this.d2 = gpio.provisionDigitalOutputPin(M2);
            this.pwm = gpio.provisionPwmOutputPin(PWMA);
        } else {
            this.d1 = gpio.provisionDigitalOutputPin(M3);
            this.d2 = gpio.provisionDigitalOutputPin(M4);
            this.pwm = gpio.provisionPwmOutputPin(PWMB);
        }
        d1.high();
        d2.low();
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
        pwm.setPwm((int)(1024 * Math.abs(speed)));
    }

    public void stop(){
        setSpeed(0);
    }
}
