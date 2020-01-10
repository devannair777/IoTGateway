package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.StateVariables;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for  compiled list of all sensor and
 * actuator states and the state flags  of each room
 */
@XmlRootElement
public class GlobalStates
{
    private List<LocalStates> localStates;

    public GlobalStates()
    {
        localStates = new ArrayList<>();
    }

    public List<LocalStates> getLocalStates() {
        return localStates;
    }

    public void setLocalStates(List<LocalStates> localStates) {
        this.localStates = localStates;
    }
}

