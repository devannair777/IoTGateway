package MobileComputing.Simulation;

import MobileComputing.IoTGateway.Core.IoTGateway;
import MobileComputing.SensorEnvironment.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.californium.core.CoapResource;

import java.util.HashMap;

public class SensorEnvTest {

    private static HashMap<Integer,String> epLocn = new HashMap<>();
    private static int devCount = 0;

    public static void EnvSetup() throws InterruptedException {
        HashMap<Integer,String> epLocnMap = new HashMap<>();

        epLocnMap.put(5685,"Room 3");
        epLocnMap.put(5686,"Room 3");
        epLocnMap.put(5687,"Room 3");
        epLocnMap.put(5688,"Room 3");

        epLocnMap.put(5689,"Room 2");
        epLocnMap.put(5690,"Room 2");
        epLocnMap.put(5691,"Room 2");
        epLocnMap.put(5692,"Room 2");

        epLocnMap.put(5693,"Room 1");
        epLocnMap.put(5694,"Room 1");
        epLocnMap.put(5695,"Room 1");
        epLocnMap.put(5696,"Room 1");

        room1Setup();
        room2Setup();
        room3Setup();

        IoTGateway iotGateway = new IoTGateway();
        iotGateway.setEndpointLocation(epLocnMap);
        iotGateway.gateWayProcess();


    }

    public static void roomSetup(String filename,String roomname)
    {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", filename, resourceClass.temperature);

        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", filename, resourceClass.flash);

        ResourceQueue foamExt = SpawnElements.spawnActuatorResource(
                "foamExt", filename,false);

        ResourceQueue alarm = SpawnElements.spawnActuatorResource(
                "alarm", filename,true);

        SpawnElements.spawnSensor(5685, tempSensor);
        SpawnElements.spawnSensor(5686, irSensor);
        SpawnElements.spawnSensor(5687, foamExt);
        SpawnElements.spawnSensor(5688, alarm);


        for(int i = 0;i < 5;i++)
        {
            epLocn.put(5685 + i,roomname);
            devCount += 1;
        }

    }

    public static void room3Setup()
    {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_3.txt", resourceClass.temperature);

        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_3.txt", resourceClass.flash);

        ResourceQueue foamExt = SpawnElements.spawnActuatorResource(
                "foamExt", "locn_3.txt",false);

        ResourceQueue alarm = SpawnElements.spawnActuatorResource(
                "alarm", "locn_3.txt",true);

        SpawnElements.spawnSensor(5685, tempSensor);
        SpawnElements.spawnSensor(5686, irSensor);
        SpawnElements.spawnSensor(5687, foamExt);
        SpawnElements.spawnSensor(5688, alarm);


    }

    public static void room2Setup()
    {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_2.txt", resourceClass.temperature);

        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_2.txt", resourceClass.flash);

        ResourceQueue foamExt = SpawnElements.spawnActuatorResource(
                "foamExt", "locn_2.txt",false);

        ResourceQueue alarm = SpawnElements.spawnActuatorResource(
                "alarm", "locn_2.txt",true);

        SpawnElements.spawnSensor(5689, tempSensor);
        SpawnElements.spawnSensor(5690, irSensor);
        SpawnElements.spawnSensor(5691, foamExt);
        SpawnElements.spawnSensor(5692, alarm);

    }

    public static void room1Setup()
    {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_1.txt", resourceClass.temperature);

        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_1.txt", resourceClass.flash);

        ResourceQueue foamExt = SpawnElements.spawnActuatorResource(
                "foamExt", "locn_1.txt",false);

        ResourceQueue alarm = SpawnElements.spawnActuatorResource(
                "alarm", "locn_1.txt",true);

        SpawnElements.spawnSensor(5693, tempSensor);
        SpawnElements.spawnSensor(5694, irSensor);
        SpawnElements.spawnSensor(5695, foamExt);
        SpawnElements.spawnSensor(5696, alarm);

    }

    public static void sensorActuatorGatewayIntegrationTest() throws InterruptedException {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_3.txt", resourceClass.temperature);


        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_1.txt", resourceClass.flash);


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
        epLocnMap.put(5683,"Room 3");
        epLocnMap.put(5684,"Room 1");
        epLocnMap.put(5685,"Room 2");
        epLocnMap.put(5686,"Room 3");
        epLocnMap.put(5687,"Room 3");

        IoTGateway iotGateway = new IoTGateway();
        iotGateway.setEndpointLocation(epLocnMap);
        iotGateway.gateWayProcess();
    }

    public static void SensorGatewayIntegrationTest() throws InterruptedException, JsonProcessingException {
        ResourceQueue tempSensor = SpawnElements.spawnSensorResource(
                "temp", "locn_1.txt", resourceClass.temperature);
        ResourceQueue irSensor = SpawnElements.spawnSensorResource(
                "irSense", "locn_1.txt", resourceClass.flash);
        ResourceQueue tempSensor2 = SpawnElements.spawnSensorResource(
                "temp", "locn_2.txt", resourceClass.temperature);
        SpawnElements.spawnSensor(5683, tempSensor);
        SpawnElements.spawnSensor(5684, irSensor);
        SpawnElements.spawnSensor(5685, tempSensor2);

        HashMap<Integer,String> epLocnMap = new HashMap<>();
        epLocnMap.put(5683,"Room 1");
        epLocnMap.put(5684,"Room 1");
        epLocnMap.put(5685,"Room 2");

        IoTGateway iotGateway = new IoTGateway();
        iotGateway.setEndpointLocation(epLocnMap);
        iotGateway.gateWayProcess();
    }

}
