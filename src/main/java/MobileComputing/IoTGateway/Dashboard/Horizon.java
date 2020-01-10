package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.IoTGateway;
import MobileComputing.IoTGateway.Core.StateVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Horizon
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Horizon.class.getCanonicalName());
    public Horizon()
    {}

    public static StateVariables  getLocnSensorData(String locn)
    {
        StateVariables sv = IoTGateway.getGlobalStates().get(locn);
        return sv;
    }
/*

    public static void setGatewayParameterThresholds(StateVariables sv)
    {
        StateVariables.setFlashThres(sv.getFlash());
        */
/*StateVariables.setHumidityThres(sv.getHumidity());*//*

        StateVariables.setTempThres(sv.getTemperature());
        StateVariables.setSmokeThres(sv.getSmoke());
    }
*/

    public static GlobalStates getStatesFromAllLocations()
    {
        GlobalStates globalStates = new GlobalStates();
        try {

            List<LocalStates> compileRes = new ArrayList<>();
            getLocationIds().getLocationIds().forEach(
                    (locn) -> {
                        LocalStates ls = new LocalStates();
                        StateVariables sv = IoTGateway.getGlobalStates().get(locn);
                        ls.setLocation(locn);
                        ls.setStateVariable(sv);
                        ls.setFire(IoTGateway.getIsFireLocn().get(locn));
                        ls.setSmokeAlert(IoTGateway.getSmokeWarn().get(locn));
                        compileRes.add(ls);

                    }
            );
            globalStates.setLocalStates(compileRes);
        }
        catch (NullPointerException npe)
        {
            LOGGER.error("Null Pointer Exception at Horizon.Restart project and open browser after atleast one timestep");
        }
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
