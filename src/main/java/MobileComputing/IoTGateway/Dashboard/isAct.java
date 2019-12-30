package MobileComputing.IoTGateway.Dashboard;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class isAct
{
    private String actEnable;

    public isAct() {
    }

    public String getActEnable() {
        return actEnable;
    }

    public void setActEnable(String actEnable) {
        this.actEnable = actEnable;
    }
}
