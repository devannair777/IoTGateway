package MobileComputing.SensorEnvironment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ActuationCommand {
    private double val;
    private int numTimes;
    private static ObjectMapper jsonParser;

    public ActuationCommand() {
        jsonParser = new ObjectMapper();
    }

    public double getVal() {
        return val;
    }

    public void setVal(double val) {
        this.val = val;
    }

    public int getNumTimes() {
        return numTimes;
    }

    public void setNumTimes(int numTimes) {
        this.numTimes = numTimes;
    }

    public String serializedCommand() throws JsonProcessingException {
        return jsonParser.writeValueAsString(this);
    }

    public ActuationCommand deserializedCommand(String command) throws IOException {
        return jsonParser.readValue(command, this.getClass());
    }

}
