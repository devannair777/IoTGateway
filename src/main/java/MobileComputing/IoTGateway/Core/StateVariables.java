package MobileComputing.IoTGateway.Core;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StateVariables {
    private double temperature;
    private double luminosity;
    private double humidity;

    public StateVariables() {
    }

    public double getTemperature() {
        return temperature;
    }

    public static boolean compareWithThreshold(StateVariables sv) {
        StateVariables threshold = new StateVariables();
        threshold.setHumidity(18);
        threshold.setLuminosity(18);
        threshold.setTemperature(18);

        if ((sv.getHumidity() > threshold.getHumidity())
                | (sv.getLuminosity() > threshold.getLuminosity())
                | (sv.getTemperature() > threshold.getTemperature())) {
            return false;
        } else {
            return true;
        }
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(double luminosity) {
        this.luminosity = luminosity;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
