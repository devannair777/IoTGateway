package MobileComputing.SensorEnvironment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setValue(String value) {
        this.value = value;
    }

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
