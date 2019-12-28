package MobileComputing.IoTGateway.Core;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StateVariables {
    private double temperature;
    private double flash;
    private double humidity;

    public StateVariables() {
    }

    public double getTemperature() {
        return temperature;
    }

    public static boolean compareWithThreshold(StateVariables sv) {
        StateVariables threshold = new StateVariables();
        threshold.setHumidity(18);
        threshold.setFlash(18);
        threshold.setTemperature(18);

        if ((sv.getHumidity() > threshold.getHumidity())
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

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
