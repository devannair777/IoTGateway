package MobileComputing.IoTGateway.Core;

import MobileComputing.SensorEnvironment.SensorState;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;


public class CoapObserverHandler implements CoapHandler {
    private static HashMap<String, HashMap> globalStates;
    private SensorState sensorState;
    private static final Logger LOGGER = LoggerFactory.getLogger(CoapObserverHandler.class.getCanonicalName());

    public static HashMap<String, HashMap> getCurrentState() {
        return globalStates;
    }

    @Override
    public void onLoad(CoapResponse response) {
        try {
            int portnum = response.advanced().getSourceContext().getPeerAddress().getPort();
            String srcLoc = new StringBuilder().append(IoTGateway.getCoapScheme())
                    .append(IoTGateway.getHostname())
                    .append(portnum)
                    .toString();

            String locationEpCtx = IoTGateway.findLocnfromEpContext(srcLoc);

            sensorState = this.sensorState.deserializeJSON(response.getResponseText());

            if (globalStates.containsKey(locationEpCtx)) {
                globalStates.get(locationEpCtx).put(sensorState.getParameter(), sensorState.getValue());
            } else {
                HashMap<String, String> temp = new HashMap<>();
                temp.put(sensorState.getParameter(), sensorState.getValue());
                globalStates.put(locationEpCtx, temp);
            }

            LOGGER.info("Notification from Sensor : " + response.getResponseText() + "\n");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError()
    {
        /*TODO : Timeout Handling*/
        LOGGER.error("CoapObserve Failed");
    }
}
