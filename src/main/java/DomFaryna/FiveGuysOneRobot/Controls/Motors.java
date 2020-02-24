package DomFaryna.FiveGuysOneRobot.Controls;

import com.pi4j.wiringpi.Gpio;

// Turns out the motors just use PWM on certain GPIO ports. Sweet and easy
public class Motors {
    // well, the wiring is detecting by the board, not the
    // pins, so I think its fine to leave it here
    private final int PWMA1 = 6;
    private final int PWMA2 = 13;
    private final int PWMB1 = 20;
    private final int PWMB2 = 21;
    private final int D1 = 12;
    private final int D2 = 26;

    private int PWMA;
    private int PWMB;
    private int D;
    private boolean forward = true;

    public Motors(boolean isRight) {
        Gpio.pwmSetMode(Gpio.PWM_MODE_BAL);
        if(isRight){
            this.PWMA = PWMA1;
            this.PWMB = PWMB1;
            this.D = D1;
        } else {
            this.PWMA = PWMA2;
            this.PWMB = PWMB2;
            this.D = D2;
        }
        Gpio.pinMode(PWMA, Gpio.OUTPUT);
        Gpio.pinMode(PWMB, Gpio.OUTPUT);
        Gpio.digitalWrite(PWMA, 1);
        Gpio.digitalWrite(PWMB, 0);
        Gpio.pinMode(D, Gpio.PWM_OUTPUT);
    }

    public void setSpeed(double speed){
        if(forward && speed < 0.0){
            forward = false;
            Gpio.digitalWrite(PWMA, 0);
            Gpio.digitalWrite(PWMB, 1);
        } else if(!forward && speed > 0.0){
            forward = true;
            Gpio.digitalWrite(PWMA, 1);
            Gpio.digitalWrite(PWMB, 0);
        }
        Gpio.pwmWrite(D, (int)(1024 * Math.abs(speed)));
    }

    public void stop(){
        setSpeed(0);
    }
}
