package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.IoTGateway;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebService {
    @RequestMapping(value = "/horizon", method = RequestMethod.GET)
    public String dashboard(Model model)
    {
        model.addAttribute("viewname","Dashboard");
        model.addAttribute("temp", IoTGateway.getGlobalStates().get("locn_1").getTemperature());
        model.addAttribute("humidity", IoTGateway.getGlobalStates().get("locn_1").getHumidity());
        model.addAttribute("luminosity", IoTGateway.getGlobalStates().get("locn_1").getLuminosity());
        return "home";
    }
}
