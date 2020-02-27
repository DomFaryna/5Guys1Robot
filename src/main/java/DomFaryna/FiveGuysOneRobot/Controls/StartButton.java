package DomFaryna.FiveGuysOneRobot.Controls;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

// Button is used to wait until a button pressed before going on
public class StartButton {
    //private final Pin dPin = RaspiBcmPin.GPIO_19;
    private final Pin dPin = RaspiPin.GPIO_24;
    private final GpioPinDigitalInput button;

    public StartButton() {
        GpioController gpio = GpioFactory.getInstance();
        button = gpio.provisionDigitalInputPin(dPin, PinPullResistance.PULL_DOWN);
        button.setDebounce(100);
    }

    // waits for the pin to be pressed
    public void waitForPress() {
        // Add a listener to start the program iff the button has ben pressed once and only once
        synchronized (button){
            button.addListener((GpioPinListenerDigital) event -> {
                synchronized (button) {
                    System.out.println(String.format("Got event %s", event.getEdge().getName()));
                    if (event.getEdge() != PinEdge.RISING) {
                        return;
                    }
                    button.removeAllListeners();
                    button.notify();
                }
            });
            try {
                button.wait();
                // Safety sleep so that the robot doesn't just YEEEEEEET when its time to go
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // tests for a wait and the prints
    public void waitForPressAndPrint() {
        System.out.println("Gonna wait for a press");
        waitForPress();
        System.out.println("Got a press. No longer waiting!!!");
    }

    public boolean getON() {
        return button.isHigh();
    }
}
