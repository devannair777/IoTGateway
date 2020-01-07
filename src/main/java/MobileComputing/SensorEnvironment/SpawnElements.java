package MobileComputing.SensorEnvironment;


import org.eclipse.californium.core.coap.MediaTypeRegistry;

public class SpawnElements {
    public static ResourceQueue spawnSensorResource(String resName, String filename, resourceClass rs) {
        ResourceQueue rq = new ResourceQueue(resName);
        rq.setEnvParameter(filename);
        rq.setresourceType(rs);
        rq.setInterfaceType(interfaceClass.Sensor);
        rq.getAttributes().addInterfaceDescription(interfaceClass.Sensor.toString());
        rq.getAttributes().addResourceType(rs.toString());
        rq.getAttributes().addContentType(MediaTypeRegistry.APPLICATION_JSON);
        //rq.getAttributes().setObservable();
        return rq;
    }

    public static ResourceQueue spawnActuatorResource(String resName, String filename,boolean isGuidance) {
        ResourceQueue rs = new ResourceQueue(resName);
        rs.setEnvParameter(filename);
        if(! isGuidance)
        {
            rs.setInterfaceType(interfaceClass.Actuator);
            rs.getAttributes().addInterfaceDescription(interfaceClass.Actuator.toString());
        }
        else
        {
            rs.setInterfaceType(interfaceClass.GuidanceActuator);
            rs.getAttributes().addInterfaceDescription(interfaceClass.GuidanceActuator.toString());
        }
        return rs;
    }

    public static ResourceQueue spawnActuator(int portNum, ResourceQueue act) {
        Interface i2 = new Interface(portNum);
        i2.addResource(act);
        i2.runInterface();
        //act.getParent().add(new CoapResource("firmware"));
        return act;
    }

    public static ResourceQueue spawnSensor(int portNum, ResourceQueue sens) {
        Interface i1 = new Interface(portNum);
        i1.addResource(sens);
        i1.runInterface();
        //sens.getParent().add(new CoapResource("firmware"));
        return sens;
    }

    public static ObservableResourceQueue spawnObservableResource(String resName,String filename,resourceClass rs)
    {
        ObservableResourceQueue obsRq = new ObservableResourceQueue(resName);
        obsRq.setEnvParameter(filename);
        obsRq.setResourceType(rs);
        obsRq.getAttributes().setObservable();
        obsRq.setInterfaceType(interfaceClass.Sensor);
        obsRq.getAttributes().addInterfaceDescription(interfaceClass.Sensor.toString());
        obsRq.getAttributes().addResourceType(rs.toString());
        obsRq.getAttributes().addContentType(MediaTypeRegistry.APPLICATION_JSON);

        return obsRq;

    }

    public static ObservableResourceQueue spawnObservableSensor(int portNum,ObservableResourceQueue obsRq)
    {
        Interface i1 = new Interface(portNum);
        i1.addResource(obsRq);
        i1.runInterface();
        return obsRq;
    }

}
