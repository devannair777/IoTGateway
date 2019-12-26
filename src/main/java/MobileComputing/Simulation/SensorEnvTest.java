package MobileComputing.Simulation;

import MobileComputing.IoTGateway.Core.IoTGateway;
import MobileComputing.SensorEnvironment.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;

public class SensorEnvTest {
 /*   public static void main(String[] args) throws IOException {
        try {
            sensorActuatorGatewayIntegrationTest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public static void sensorActuatorGatewayIntegrationTest() throws InterruptedException {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_3.txt", resourceClass.temperature);
        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_1.txt", resourceClass.irSpectrum);
        ResourceQueue tempSensor2 = SpawnElements.spawnSensorResource(
                "temp", "locn_2.txt", resourceClass.temperature);
        ResourceQueue foamExt = SpawnElements.spawnActuatorResource(
                "foamExt", "locn_3.txt",false);
        ResourceQueue alarm = SpawnElements.spawnActuatorResource(
                "alarm", "locn_3.txt",true);

        SpawnElements.spawnSensor(5683, tempSensor);
        SpawnElements.spawnSensor(5684, irSensor);
        SpawnElements.spawnSensor(5685, tempSensor2);
        SpawnElements.spawnActuator(5686, foamExt);
        SpawnElements.spawnActuator(5687, alarm);

        HashMap<Integer,String> epLocnMap = new HashMap<>();
        epLocnMap.put(5683,"locn_3");
        epLocnMap.put(5684,"locn_1");
        epLocnMap.put(5685,"locn_2");
        epLocnMap.put(5686,"locn_3");
        epLocnMap.put(5687,"locn_3");

        IoTGateway iotGateway = new IoTGateway();
        iotGateway.setEndpointLocation(epLocnMap);
        //SpawnElements.spawnRestServer("http://localhost:8080/api", "MobileComputing.IoTGateway");
        iotGateway.gateWayProcess();
    }

    public static void SensorGatewayIntegrationTest() throws InterruptedException, JsonProcessingException {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_1.txt", resourceClass.temperature);
        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_1.txt", resourceClass.irSpectrum);
        ResourceQueue tempSensor2 = SpawnElements.spawnSensorResource(
                "temp", "locn_2.txt", resourceClass.temperature);
        SpawnElements.spawnSensor(5683, tempSensor);
        SpawnElements.spawnSensor(5684, irSensor);
        SpawnElements.spawnSensor(5685, tempSensor2);

        HashMap<Integer,String> epLocnMap = new HashMap<>();
        epLocnMap.put(5683,"locn_1");
        epLocnMap.put(5684,"locn_1");
        epLocnMap.put(5685,"locn_2");

        IoTGateway iotGateway = new IoTGateway();
        iotGateway.setEndpointLocation(epLocnMap);
        iotGateway.gateWayProcess();
    }

}
