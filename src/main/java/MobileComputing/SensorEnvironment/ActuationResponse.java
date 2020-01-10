package MobileComputing.SensorEnvironment;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ActuationResponse {

    private static ObjectMapper jsonParser;
    private String response;

    public ActuationResponse() {
        jsonParser = new ObjectMapper();
    }

}
