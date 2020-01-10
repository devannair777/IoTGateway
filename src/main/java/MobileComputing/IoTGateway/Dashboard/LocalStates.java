package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.StateVariables;

/**
 * Sensor and Actuator states and room state for one
 * particular room
 */
public class LocalStates
{
    private String location ="";
    private StateVariables stateVariable;
    private boolean isFire = false;
    private boolean smokeAlert = false;

    public boolean isSmokeAlert() {
        return smokeAlert;
    }

    public void setSmokeAlert(boolean smokeAlert) {
        this.smokeAlert = smokeAlert;
    }

    public boolean isFire() {
        return isFire;
    }

    public void setFire(boolean fire) {
        isFire = fire;
    }

    public  LocalStates()
    {
        stateVariable = new StateVariables();
    }

    public StateVariables getStateVariable() {
        return stateVariable;
    }

    public void setStateVariable(StateVariables stateVariable) {
        this.stateVariable = stateVariable;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
