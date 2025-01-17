package MobileComputing.IoTGateway.Core;

import MobileComputing.SensorEnvironment.SensorState;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Response handler for handling messages from sensors in emulator environment asynchronously
 */
public class SensorResponseHandler implements CoapHandler {
    private static HashMap<String, HashMap> globalStates;
    private SensorState sensorState;
    private final Logger LOGGER = LoggerFactory.getLogger(SensorResponseHandler.class.getCanonicalName());

    public SensorResponseHandler() {
        this.sensorState = new SensorState();
        globalStates = new HashMap<>();
    }

    /**
     *
     * @return Returns compiled state of responses from all sensors in the emulator environment classified
     * on basis of location Id
     */
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
                    //.append(response.getSourceContext().toString().substring(13,17))
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



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError()
    {
        /*TODO : Timeout Handling*/
    }
}
