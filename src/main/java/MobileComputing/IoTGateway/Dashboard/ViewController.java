package MobileComputing.IoTGateway.Dashboard;

import MobileComputing.IoTGateway.Core.IoTGateway;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * WebMvC view controller
 */
@Controller
public class ViewController {

    @RequestMapping(value = "/console", method = RequestMethod.GET)
    public String dashboard(Model model)
    {
        model.addAttribute("viewname","Dashboard");
        return "home";
    }

}
