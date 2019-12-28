package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.StateVariables;

public class LocalStates
{
    private String location ="";
    private StateVariables stateVariable;

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
