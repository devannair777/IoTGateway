package MobileComputing.SensorEnvironment;


import org.eclipse.californium.core.coap.MediaTypeRegistry;

/**
 * Class with static methods for creating the resources and
 * server instances for emulating an IoT sensor-actuator ReSTful Environment using CoAP a.k.a CoRE
 */
public class SpawnElements {

    /**
     * Create a resource which will emulate a sensor
     * @param resName URI under which the resource will reside
     * @param filename Environment file where representation for this resource is stored
     * @param rs Resource type
     * @return CoAP resource which can be hosted in CoAP server
     */
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

    /**
     *Creates a CoAP resource that can host a actuator CoAP resource in the emulation environment
     * @param resName URI under which actuator resource resides
     * @param filename Environment file corresonding to the room where this actuator resides in case scenario
     * @param isGuidance whether the given resource is a resource endpoint for an alrm and guidance system
     * @return Resource with the above properties of an actuator
     */
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

    /**
     * Creates a CoAP server hosting an actuator resource in the emulation
     * environment
     * @param portNum L4 Port number at which the CoAP resource resides
     * @param act Actuator Resource which is to be hosted on the CoAP server
     * @return CoAP server with the above properties
     */
    public static ResourceQueue spawnActuator(int portNum, ResourceQueue act) {
        Interface i2 = new Interface(portNum);
        i2.addResource(act);
        i2.runInterface();
        //act.getParent().add(new CoapResource("firmware"));
        return act;
    }

    /**
     *Creates a CoAP server hosting an sensor resource in the emulation
     * environment
     * @param portNum L4 Port number at which the CoAP resource resides
     * @param sens Sensor Resource which is to be hosted on the CoAP server
     * @return CoAP server with the above properties
     */
    public static ResourceQueue spawnSensor(int portNum, ResourceQueue sens) {
        Interface i1 = new Interface(portNum);
        i1.addResource(sens);
        i1.runInterface();
        //sens.getParent().add(new CoapResource("firmware"));
        return sens;
    }
    /**
     * Create a CoAP Observable resource which will emulate a finite state machine sensor RFC
     * @param resName URI under which the resource will reside
     * @param filename Environment file where representation for this resource is stored
     * @param rs Resource type
     * @return CoAP resource which can be hosted in CoAP server
     */
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
    /**
     *Creates a CoAP server hosting an sensor resource in the emulation
     * environment
     * @param portNum L4 Port number at which the CoAP resource resides
     * @param obsRq Observable Sensor Resource which is to be hosted on the CoAP server
     * @return CoAP server with the above properties
     */
    public static ObservableResourceQueue spawnObservableSensor(int portNum,ObservableResourceQueue obsRq)
    {
        Interface i1 = new Interface(portNum);
        i1.addResource(obsRq);
        i1.runInterface();
        return obsRq;
    }

}
