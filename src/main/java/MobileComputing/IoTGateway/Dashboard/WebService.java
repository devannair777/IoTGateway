package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.StateVariables;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WebService
{
    private static String[] locnIds = new String[10];

    @GetMapping(value = "/data/locn",produces = MediaType.APPLICATION_XML_VALUE)
    public LocationId getLocationIds()
    {
       return Horizon.getLocationIds();
    }

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


}
