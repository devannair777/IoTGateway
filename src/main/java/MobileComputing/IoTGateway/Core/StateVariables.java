package MobileComputing.IoTGateway.Core;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper Class for compiling all sensor measurement and actuator state values in
 * a particular room
 */
@XmlRootElement
public class StateVariables {
    private double temperature;
    private double flash;
    private double smoke;
    //private double humidity;

    public double getSmoke() {
        return smoke;
    }

    public void setSmoke(double smoke) {
        this.smoke = smoke;
    }

    private boolean actuatorState;

    private static double tempThres = 58;
    private static double flashThres = 38;
    private static double smokeThres = 1;

    public static double getSmokeThres() {
        return smokeThres;
    }

    public static void setSmokeThres(double smokeThres) {
        StateVariables.smokeThres = smokeThres;
    }

    public static double getTempThres() {
        return tempThres;
    }

    public static void setTempThres(double tempThres) {
        StateVariables.tempThres = tempThres;
    }

    public static double getFlashThres() {
        return flashThres;
    }

    public static void setFlashThres(double flashThres) {
        StateVariables.flashThres = flashThres;
    }

    public boolean isActuatorState() {
        return actuatorState;
    }

    public void setActuatorState(boolean actuatorState) {
        this.actuatorState = actuatorState;
    }

    public StateVariables() {
    }

    public double getTemperature() {
        return temperature;
    }

    public static boolean isSmoke(StateVariables sv)
    {
        if(sv.getSmoke() > smokeThres)
        {
            return true;
        }
        else
        {
            return  false;
        }
    }

    /**
     * Hard comparison of a state's sensor measurement value with
     * threshold to determine if a room has caught fire
     *
     * @param sv State variable of room which you have to determine is in fire or not
     * @return true if there is no fire in room ; else false
     */
    public static boolean compareWithThreshold(StateVariables sv) {
        StateVariables threshold = new StateVariables();
        threshold.setFlash(flashThres);
        threshold.setSmoke(smokeThres);
        threshold.setTemperature(tempThres);

        if ( (sv.getFlash() > threshold.getFlash())
                | (sv.getTemperature() > threshold.getTemperature())) {
            return false;
        } else {
            return true;
        }
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getFlash() {
        return flash;
    }

    public void setFlash(double flash) {
        this.flash = flash;
    }
}
