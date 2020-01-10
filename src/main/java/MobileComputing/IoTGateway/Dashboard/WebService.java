package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.IoTGateway;
import MobileComputing.IoTGateway.Core.StateVariables;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebService
{
    private static String[] locnIds = new String[10];

    /**
     *
     * @return Returns Location Id of all rooms which the gateway is monitoring
     */
    @GetMapping(value = "/data/locn",produces = MediaType.APPLICATION_XML_VALUE)
    public LocationId getLocationIds()
    {
       return Horizon.getLocationIds();
    }

    /**
     *
     * @param locn Loaction Id of the room
     * @return Returns Data from all Sensors in that location
     */
    @GetMapping(value = "/data/{locn}",produces = MediaType.APPLICATION_XML_VALUE)
    public StateVariables getLocationParameters(@PathVariable("locn") String locn)
    {
        return Horizon.getLocnSensorData(locn);
    }


    @GetMapping(value = "/data/globalStates",produces = MediaType.APPLICATION_XML_VALUE)
    public GlobalStates getLocationsParameters()
    {
        return Horizon.getStatesFromAllLocations();
    }

    /**
     *
     * @param actEnable Enable actuation flag
     */
    @PostMapping(value = "/data/isAct",consumes = MediaType.APPLICATION_XML_VALUE)
    public void setActuationEnable(@RequestBody isAct actEnable)
    {
        if(actEnable.getActEnable().equalsIgnoreCase("true"))
        {
            IoTGateway.setIsAct(true);
        }
        else
        {
            IoTGateway.setIsAct(false);
        }
    }

}
