package DomFaryna.FiveGuysOneRobot.Controls;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

// Button is used to wait until a button pressed before going on
public class Button {
    // TODO NEED TO DOUBLE CHECK PIN
    private final Pin dPin = RaspiBcmPin.GPIO_10;
    private final GpioPinDigitalInput button;

    public Button(Thread t) {
        GpioController gpio = GpioFactory.getInstance();
        button = gpio.provisionDigitalInputPin(dPin, PinPullResistance.PULL_DOWN);
        button.setDebounce(100);
    }

    // waits for the pin to be pressed
    void waitForPress() {
        // Add a listener to start the program iff the button has ben pressed once and only once
        synchronized (button){
            button.addListener(new GpioPinListenerDigital() {
                @Override
                public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                    if(event.getEdge() != PinEdge.RISING){
                        return;
                    }
                    button.removeAllListeners();
                    button.notify();
                }
            });
            try {
                button.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // tests for a wait and the prints
    void waitForPressAndPrint(){
        System.out.println("Gonna wait for a press");
        waitForPress();
        System.out.println("Got a press");
    }
}
