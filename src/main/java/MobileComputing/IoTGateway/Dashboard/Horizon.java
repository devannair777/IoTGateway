package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.IoTGateway;
import MobileComputing.IoTGateway.Core.StateVariables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Horizon
{
    public Horizon()
    {}

    public static StateVariables  getLocnSensorData(String locn)
    {
        StateVariables sv = IoTGateway.getGlobalStates().get(locn);
        return sv;
    }

    public static GlobalStates getStatesFromAllLocations()
    {
        GlobalStates globalStates = new GlobalStates();
        List<LocalStates> compileRes = new ArrayList<>();
        getLocationIds().getLocationIds().forEach(
                (locn)->{
                    LocalStates ls = new LocalStates();
                    StateVariables sv = IoTGateway.getGlobalStates().get(locn);
                    ls.setLocation(locn);
                    ls.setStateVariable(sv);
                    compileRes.add(ls);

                }
        );
        globalStates.setLocalStates(compileRes);
        return globalStates;
    }

    public static LocationId getLocationIds()
    {
        LocationId locnIds = new LocationId();
        IoTGateway.getGlobalStates().forEach(
                (locn,sv)->{
                    locnIds.addId(locn);
                }
        );
        return locnIds;
    }
}
