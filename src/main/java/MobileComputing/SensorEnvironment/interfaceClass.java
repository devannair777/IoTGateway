package MobileComputing.SensorEnvironment;

import java.util.ArrayList;

public enum interfaceClass {
    UD(resourceClass.UD),
    Sensor,
    /*TemperatureSensor(resourceClass.temperature),
    HumiditySensor(resourceClass.humidity),
    iRSensor(resourceClass.irSpectrum),*/
    GuidanceActuator,
    Actuator(resourceClass.humidity, resourceClass.flash, resourceClass.temperature);

    private ArrayList<resourceClass> modVars = new ArrayList<resourceClass>();

    interfaceClass(resourceClass... t) {
        for (resourceClass i : t) {
            modVars.add(i);
        }
    }

    public ArrayList<resourceClass> getModVars() {
        return this.modVars;
    }

}
