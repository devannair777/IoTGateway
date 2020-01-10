package MobileComputing.SensorEnvironment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON Mapping class for messages sent by sensor to the gateway
 */
public class SensorState {
    private static ObjectMapper jsonParser;

    public SensorState() {
        jsonParser = new ObjectMapper();
    }

    @Override
    public String toString() {
        return "SensorState{" +
                "parameter='" + parameter + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    /**
     * The resource type of sensor
     * @return resource type of the representation of sensor
     */
    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * The representation of the sensor
     * @return The corresponding representation of the sensor resource
     */
    public String getValue() {
        return value;
    }

    public String serializedState() throws JsonProcessingException {

        return jsonParser.writeValueAsString(this);

    }

    public SensorState deserializeJSON(String jsonString) throws IOException {
        return jsonParser.readValue(jsonString, this.getClass());
    }

    private String parameter;
    private String value;


}
