package DomFaryna.FiveGuysOneRobot.Controls;

/**
 * Class that does PID calculation for motion control
 *
 * Somewhat stolen from code I developed for Dave
 */
public class PID {

    private double p; //kP
    private double i; //kI
    private double d; //kD
    private double f; //kF

    private double e; //current error
    private double prev_e; //previous error
    private double total_e; //cumulative error

    private double t; //current time
    private double last_t; //time of last iteration

    public PID(double kp, double ki, double kd) {
        p = kp;
        i = ki;
        d = kd;
        zero();
    }

    public void zero() {
        zero(0, 0);
    }

    public void zero(double target, double observed) {
        total_e = 0;
        prev_e = target - observed;
    }

    /**
     * Performs one iteration of a PID loop
     *
     * @param target   target setpoint
     * @param observed actual location
     * @return output clamped from -1 to 1 of the PID calculation
     */
    public double iterate(double target, double observed) {
        double output = 0;

        // time slice should be in terms of seconds, not milliseconds
        t = System.currentTimeMillis() / 1000.0;
        double dt = t - last_t;
        last_t = t;

        e = target - observed;
        double de = e - prev_e;
        prev_e = e;
        total_e += e * dt;

        //the PID formula is output = weighted proportional error + weighted integrated error + weighted differentiated error
        output = p * e + i * total_e + d * (de / dt);


        // clamp
        if (output > 1.0) {
            output = 1.0;
        }

        if (output < -1.0) {
            output = -1.0;
        }
        return output;
    }

}
