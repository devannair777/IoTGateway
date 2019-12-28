package MobileComputing.IoTGateway.Dashboard;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class LocationId {
    private List<String> locationIds;
    public  LocationId()
    {
        locationIds = new ArrayList<>();
    }

    public List<String> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<String> locationIds) {
        this.locationIds = locationIds;
    }

    public void addId(String id)
    {
        this.locationIds.add(id);
    }



}
