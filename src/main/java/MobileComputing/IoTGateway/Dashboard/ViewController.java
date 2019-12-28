package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.IoTGateway;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;

@Controller
public class ViewController {

    @RequestMapping(value = "/console", method = RequestMethod.GET)
    public String dashboard(Model model)
    {
        model.addAttribute("viewname","Dashboard");
        /*model.addAttribute("location","Location 3");
        model.addAttribute("temp", IoTGateway.getGlobalStates().get("locn_3").getTemperature());
        model.addAttribute("humidity", IoTGateway.getGlobalStates().get("locn_3").getHumidity());
        model.addAttribute("luminosity", IoTGateway.getGlobalStates().get("locn_3").getLuminosity());*/
        return "home";
    }

    @RequestMapping(value = "/map", method = RequestMethod.GET)
    public String locationMap(Model model)
    {
        model.addAttribute("viewname","Sensor Actuator Map");

        return "map";
    }

    @RequestMapping(value = "/firmware", method = RequestMethod.GET)
    public String firmwareDetails(Model model)
    {
        model.addAttribute("viewname","Firmware Details");

        return "firmware";
    }
}
