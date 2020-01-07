package MobileComputing.Simulation;

import MobileComputing.IoTGateway.Core.IoTGateway;
import MobileComputing.SensorEnvironment.*;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;

public class SensorEnvTest {

    public static void EnvSetup() throws InterruptedException {
        HashMap<Integer,String> epLocnMap = new HashMap<>();

        epLocnMap.put(5685,"Room 3");
        epLocnMap.put(5686,"Room 3");
        epLocnMap.put(5687,"Room 3");
        epLocnMap.put(5688,"Room 3");
        epLocnMap.put(5689,"Room 3");

        epLocnMap.put(5690,"Room 2");
        epLocnMap.put(5691,"Room 2");
        epLocnMap.put(5692,"Room 2");
        epLocnMap.put(5693,"Room 2");
        epLocnMap.put(5694,"Room 2");

        epLocnMap.put(5695,"Room 1");
        epLocnMap.put(5696,"Room 1");
        epLocnMap.put(5697,"Room 1");
        epLocnMap.put(5698,"Room 1");
        epLocnMap.put(5699,"Room 1");

        room1Setup();
        room2Setup();
        room3Setup();

        IoTGateway iotGateway = new IoTGateway();
        iotGateway.setEndpointLocation(epLocnMap);
        iotGateway.gateWayProcess();


    }

    public static void room3Setup()
    {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_3.txt", resourceClass.temperature);

        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_3.txt", resourceClass.flash);

        ObservableResourceQueue smokeSensor = SpawnElements.spawnObservableResource(
                "smoke","locn_3.txt",resourceClass.smoke
        );

        ResourceQueue foamExt = SpawnElements.spawnActuatorResource(
                "foamExt", "locn_3.txt",false);

        ResourceQueue alarm = SpawnElements.spawnActuatorResource(
                "alarm", "locn_3.txt",true);

        SpawnElements.spawnSensor(5685, tempSensor);
        SpawnElements.spawnSensor(5686, irSensor);
        SpawnElements.spawnSensor(5687, smokeSensor);

        SpawnElements.spawnSensor(5688, foamExt);
        SpawnElements.spawnSensor(5689, alarm);


    }

    public static void room2Setup()
    {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_2.txt", resourceClass.temperature);

        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_2.txt", resourceClass.flash);

        ObservableResourceQueue smokeSensor = SpawnElements.spawnObservableResource(
                "smoke","locn_2.txt",resourceClass.smoke
        );

        ResourceQueue foamExt = SpawnElements.spawnActuatorResource(
                "foamExt", "locn_2.txt",false);

        ResourceQueue alarm = SpawnElements.spawnActuatorResource(
                "alarm", "locn_2.txt",true);

        SpawnElements.spawnSensor(5690, tempSensor);
        SpawnElements.spawnSensor(5691, irSensor);
        SpawnElements.spawnSensor(5692, smokeSensor);

        SpawnElements.spawnSensor(5693, foamExt);
        SpawnElements.spawnSensor(5694, alarm);

    }

    public static void room1Setup()
    {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_1.txt", resourceClass.temperature);

        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_1.txt", resourceClass.flash);

        ObservableResourceQueue smokeSensor = SpawnElements.spawnObservableResource(
                "smoke","locn_1.txt",resourceClass.smoke
        );

        ResourceQueue foamExt = SpawnElements.spawnActuatorResource(
                "foamExt", "locn_1.txt",false);

        ResourceQueue alarm = SpawnElements.spawnActuatorResource(
                "alarm", "locn_1.txt",true);

        SpawnElements.spawnSensor(5695, tempSensor);
        SpawnElements.spawnSensor(5696, irSensor);
        SpawnElements.spawnSensor(5697, smokeSensor);

        SpawnElements.spawnSensor(5698, foamExt);
        SpawnElements.spawnSensor(5699, alarm);
    }

}
