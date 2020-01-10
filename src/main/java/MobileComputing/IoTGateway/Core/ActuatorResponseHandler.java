package MobileComputing.IoTGateway.Core;

import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Response Handler for Actuator response messages
 */
public class ActuatorResponseHandler implements CoapHandler {
    private static HashMap<String,String> actStatus;
    private static Logger LOGGER = LoggerFactory.getLogger(ActuatorResponseHandler.class.getCanonicalName());


    public ActuatorResponseHandler()
    {
        actStatus = new HashMap<>();

    }

    /**
     *
     * @return Returns the actuator's current state
     */
    public static HashMap<String, String> getCurrentState() {
        return actStatus;
    }
    @Override
    public void onLoad(CoapResponse response)
    {
        try {
            int portnum = response.advanced().getSourceContext().getPeerAddress().getPort();
            String srcLoc = new StringBuilder().append(IoTGateway.getCoapScheme())
                    .append(IoTGateway.getHostname())
                    .append(portnum)
                    //.append(response.getSourceContext().toString().substring(13,17))
                    .toString();

            String locationEpCtx = IoTGateway.findLocnfromEpContext(srcLoc);
            //LOGGER.info("\nResponse From Actuator : " + response.getResponseText()+"\n");
            if (response.getResponseText().contains("Status"))
            {
                String temp = response.getResponseText().split(":")[1];
                String res = "";
                if(temp.contains("true"))
                {
                    res = "true";
                }
                else if(temp.contains("false"))
                {
                    res = "false";
                }
                else
                {
                    res = temp;
                }
                actStatus.put(locationEpCtx,res);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {

    }
}
