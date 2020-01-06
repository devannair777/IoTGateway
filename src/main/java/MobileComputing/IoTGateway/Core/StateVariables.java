package MobileComputing.IoTGateway.Core;

import javax.xml.bind.annotation.XmlRootElement;

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

    private static double tempThres = 18;
    private static double flashThres = 18;
    //private static double humidityThres = 18;
    private static double smokeThres = 4;

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

    /*public static double getHumidityThres() {
        return humidityThres;
    }

    public static void setHumidityThres(double humidityThres) {
        StateVariables.humidityThres = humidityThres;
    }*/

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

    public static boolean compareWithThreshold(StateVariables sv) {
        StateVariables threshold = new StateVariables();
        //threshold.setHumidity(humidityThres);
        threshold.setFlash(flashThres);
        threshold.setSmoke(smokeThres);
        threshold.setTemperature(tempThres);

        if ((sv.getSmoke() > threshold.getSmoke())
                | (sv.getFlash() > threshold.getFlash())
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
/*
    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }*/
}
