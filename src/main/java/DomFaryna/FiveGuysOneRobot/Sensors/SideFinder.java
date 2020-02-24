package DomFaryna.FiveGuysOneRobot.Sensors;

import com.pi4j.wiringpi.Gpio;

// SideFinder is the side range finder. Unlike the I2C one, this was is analog.
public class SideFinder {
    private int pin = 17;//>

    public SideFinder() {
        Gpio.pinMode(pin, Gpio.INPUT);
    }

    // Note, need to calibrate this bad boy so that we can get values that makes sense
    public int getDistance(){
        return Gpio.digitalRead(pin);
    }
}
