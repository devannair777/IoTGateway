package MobileComputing.IoTGateway.Core;

import MobileComputing.SensorEnvironment.ActuationCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class IoTGateway {

        private static final Logger LOGGER = LoggerFactory.getLogger(IoTGateway.class.getCanonicalName());
        private ArrayList<String> endPointURI;
        private ArrayList<CoapClient> coapClients;
        private ArrayList<CoapClient> coapSensors;
        private ArrayList<CoapClient> obsSensor;
        private ArrayList<CoapClient> coapActuators;
        private ArrayList<CoapClient> coapGuidanceActuator;
        private final int clientPortNum = 62235;
        private static String coapScheme = "coap://";
        private static String hostname = "localhost:";
        private static Map<String, String> endpointLocation;
        //private boolean isAct  ///Input from user whether actuation has to be performed
        private static boolean isAct = false;

        private HashMap<CoapClient, String> actuatorByLocation;
        private HashMap<CoapClient,String>  guidanceActuatorByLocation;
        private HashMap<CoapClient, String> sensorByLocation;

        private static HashMap<String,Boolean> isFireLocn;
        private static HashMap<String,Boolean> smokeWarn;

        private SensorResponseHandler sensorResponseHandler;
        private ActuatorResponseHandler actuatorResponseHandler;

        public IoTGateway() {
            endPointURI = new ArrayList<String>();
            endpointLocation = new HashMap<>();
            coapClients = new ArrayList<>();
            coapSensors = new ArrayList<>();
            obsSensor = new ArrayList<>();
            coapActuators = new ArrayList<>();
            coapGuidanceActuator = new ArrayList<>();
            actuatorByLocation = new HashMap<>();
            sensorByLocation = new HashMap<>();
            guidanceActuatorByLocation = new HashMap<>();

            isFireLocn = new HashMap<>();
            smokeWarn = new HashMap<>();

            sensorResponseHandler = new SensorResponseHandler();
            actuatorResponseHandler = new ActuatorResponseHandler();


        }

    public static HashMap<String, Boolean> getSmokeWarn() {
        return smokeWarn;
    }

    public static void setSmokeWarn(HashMap<String, Boolean> smokeWarn) {
        IoTGateway.smokeWarn = smokeWarn;
    }

    public static HashMap<String, Boolean> getIsFireLocn() {
        return isFireLocn;
    }

    public static void setIsFireLocn(HashMap<String, Boolean> isFireLocn) {
        IoTGateway.isFireLocn = isFireLocn;
    }


    public static boolean isIsAct() {
        return isAct;
    }

    public static void setIsAct(boolean isAct) {
        IoTGateway.isAct = isAct;
    }

    public void setEndpointLocation(HashMap<Integer,String> epLocnMap)
        {
            epLocnMap.keySet().forEach(
                    (ep)->{
                        endPointURI.add(
                                new StringBuilder(coapScheme)
                                        .append(hostname)
                                        .append(ep)
                                        .toString()
                        );
                    }
            );
            CoapEndpoint.CoapEndpointBuilder coapEndpointBuilder = new CoapEndpoint.CoapEndpointBuilder();
            CoapEndpoint coapEndpoint = coapEndpointBuilder.setPort(this.clientPortNum).build();
            endPointURI.forEach((ep) ->
            {
                CoapClient client = new CoapClient();
                client.setEndpoint(coapEndpoint);
                client.useCONs();
                client.setURI(ep);
                client.setTimeout(Long.valueOf(1000));
                this.coapClients.add(client);
            });

            for(String epUri : this.endPointURI)
            {
                String s = epUri.split(":")[2];
                endpointLocation.put(epUri,epLocnMap.get(Integer.valueOf(s)));
                isFireLocn.put(epLocnMap.get(Integer.valueOf(s)),false);

            }

        }

        public static String getCoapScheme() {
            return coapScheme;
        }

        public static String getHostname() {
            return hostname;
        }

        private List<CoapClient> getGuidanceActuatorFromLocation(String location) {
            ArrayList<CoapClient> actuatorClients = new ArrayList<CoapClient>();
            if (this.guidanceActuatorByLocation.containsValue(location)) {
                for (CoapClient client : this.guidanceActuatorByLocation.keySet()) {
                    if (this.guidanceActuatorByLocation.get(client).equalsIgnoreCase(location)) {
                        actuatorClients.add(client);
                    }
                }
            }

            return actuatorClients;
        }

        private List<CoapClient> getActuatorFromLocation(String location) {
            ArrayList<CoapClient> actuatorClients = new ArrayList<CoapClient>();
            if (this.actuatorByLocation.containsValue(location)) {
                for (CoapClient client : this.actuatorByLocation.keySet()) {
                    if (this.actuatorByLocation.get(client).equalsIgnoreCase(location)) {
                        actuatorClients.add(client);
                    }
                }
            }

            return actuatorClients;
        }

        public static String findLocnfromEpContext(String epCtx) {
            return endpointLocation.get(epCtx);
        }

        private void discoverResources() {
            LOGGER.info("Initiating Resource Discovery ...");
            this.coapClients.forEach((client) ->
            {
                ArrayList<WebLink> temp = new ArrayList<>();
                String epUri = client.getURI();
                Object[] links = new Object[5];
                Set<WebLink> webLinks = new HashSet<>();
                webLinks = client.discover("if=*");
                ///Add a condition to eliminate faulty endpoint entries from discovery list
                if (webLinks != null) {
                    links = webLinks.toArray();
                    //links = client.discover().toArray();
                    WebLink webLink = (WebLink) links[0];
                    String interfaceDescrptn = (webLink).getAttributes()
                            .getInterfaceDescriptions()
                            .toString();
                    interfaceDescrptn = interfaceDescrptn.substring(1, interfaceDescrptn.indexOf("]"));

                    String resURI = (webLink).getURI();
                    client.setURI(client.getURI() + resURI);
                    //this.interfaceDescription.put(client.getURI() ,interfaceDescrptn);
                    if (interfaceDescrptn.equalsIgnoreCase("Sensor")) {
                        if(webLink.getAttributes().hasObservable())
                        {
                            this.obsSensor.add(client);
                        }
                        else
                        {
                            this.coapSensors.add(client);
                        }
                        this.sensorByLocation.put(
                                client, endpointLocation.get(epUri)
                        );
                    } else if (interfaceDescrptn.equalsIgnoreCase("Actuator")) {
                        this.coapActuators.add(client);
                        this.actuatorByLocation.put(
                                client, endpointLocation.get(epUri)
                        );
                    }
                    else if (interfaceDescrptn.equalsIgnoreCase("GuidanceActuator")) {
                        this.coapGuidanceActuator.add(client);
                        this.guidanceActuatorByLocation.put(
                                client, endpointLocation.get(epUri)
                        );
                    }
                    LOGGER.info("Resource Discovered : " + (webLink).toString());
                }
            });
        }

        private void initiateActuation(CoapClient coapClient) {
            try {
                ActuationCommand command = new ActuationCommand();
                command.setNumTimes(1);
                command.setVal(3);
                coapClient.post(actuatorResponseHandler
                        , command.serializedCommand(), MediaTypeRegistry.APPLICATION_JSON);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //coapClient.get(sensorObserver);
        }

        private void initiateGuidanceActuation(CoapClient coapClient) {
            try {
                coapClient.post(actuatorResponseHandler
                        , "ToggleState", MediaTypeRegistry.APPLICATION_JSON);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //coapClient.get(sensorObserver);
        }

        public static HashMap<String, StateVariables> getGlobalStates() {
            HashMap<String, StateVariables> result = new HashMap<>();
            SensorResponseHandler.getCurrentState().forEach
                    ((locnName, hashMap) ->
                            {

                                StateVariables sv = new StateVariables();
                                hashMap.forEach(
                                        (param, value) ->
                                        {
                                            if (param.toString().equalsIgnoreCase("temperature")) {
                                                sv.setTemperature(Double.valueOf(value.toString()));
                                            } else if (param.toString().equalsIgnoreCase("flash")) {
                                                sv.setFlash(Double.valueOf(value.toString()));
                                            } /*else if (param.toString().equalsIgnoreCase("humidity")) {
                                                sv.setHumidity(Double.valueOf(value.toString()));
                                            }*/
                                            else if (param.toString().equalsIgnoreCase("smoke")) {
                                                sv.setSmoke(Double.valueOf(value.toString()));
                                            }
                                        }
                                );
                                String actState = ActuatorResponseHandler.getCurrentState().get(locnName);
                                //boolean b = Boolean.getBoolean(actState);
                                boolean b = false;
                                if(actState != null)
                                {
                                    if(actState.equalsIgnoreCase("true"))
                                    {
                                        b = true;
                                    }
                                }
                                sv.setActuatorState(b);
                                result.put(locnName, sv);
                            }
                    );
            return result;
        }

        private int numFire()
        {
            int count  = 0;
            for(boolean isFire : isFireLocn.values())
            {
                if(isFire)
                {
                    count ++;
                }
            }
            return count;
        }

        private void makeActiveDecision() {
            HashMap<String, StateVariables> currData = new HashMap<>();
            currData = IoTGateway.getGlobalStates();
            ArrayList<String> availableElements = new ArrayList<>();
            ArrayList<String> actuatorList = new ArrayList<>();
            currData.forEach(
                    (locn, stateVariables) ->
                    {
                        if (!StateVariables.compareWithThreshold(stateVariables)) {
                            if(StateVariables.isSmoke(stateVariables))
                            {
                                smokeWarn.put(locn,true);
                            }
                            else
                            {
                                smokeWarn.put(locn,false);
                            }
                            isFireLocn.put(locn,true);
                            ///if condn ( (numfire() < 2)& isact) | (numfire() >2 )
                            if((numFire() < 2 && isAct) | (numFire() > 2)) {
                                getActuatorFromLocation(locn).forEach(
                                        (actuator) -> {
                                            initiateActuation(actuator);
                                        }
                                );
                                if(ActuatorResponseHandler.getCurrentState().get(locn) == null ||
                                        ActuatorResponseHandler.getCurrentState().get(locn).equalsIgnoreCase("false"))
                                {
                                    getGuidanceActuatorFromLocation(locn).forEach(
                                            (guidanceActuator) -> {
                                                LOGGER.info("Entering State to turn on guidance actuator ");
                                                initiateGuidanceActuation(guidanceActuator);
                                            }
                                    );
                                }

                            }

                        }
                        else
                        {
                            if(StateVariables.isSmoke(stateVariables))
                            {
                                smokeWarn.put(locn,true);
                            }
                            else
                            {
                                smokeWarn.put(locn,false);
                            }
                            isFireLocn.put(locn,false);
                            if((isAct)) {
                                if(ActuatorResponseHandler.getCurrentState().get(locn) != null &&
                                        ActuatorResponseHandler.getCurrentState().get(locn).equalsIgnoreCase("true"))
                                {
                                    getGuidanceActuatorFromLocation(locn).forEach(
                                            (guidanceActuator) -> {
                                                LOGGER.info("Entering State to turn off guidance actuator ");
                                                initiateGuidanceActuation(guidanceActuator);
                                                isAct = false;
                                            }
                                    );
                                }

                            }
                        }
                    }
            );

        }

        private void initiateObserve()
        {
            for(CoapClient obsClient : this.obsSensor)
            {
                CoapObserveRelation obsRel= obsClient.observe(sensorResponseHandler);
            }
        }

        public void gateWayProcess() throws InterruptedException {
            this.discoverResources();
            this.initiateObserve();
            int time = 0;
            while (time < 200) {
                LOGGER.info("Gateway Process begins");

                for (CoapClient coapClient : this.coapSensors) {
                    coapClient.get(sensorResponseHandler);
                    //initiateActuation(coapClient);
                    Thread.sleep(500);
                }
                makeActiveDecision();
                LOGGER.info("Gateway Process timestep :" + time+" Results");
                LOGGER.info("Global Sensor States : " + SensorResponseHandler.getCurrentState().toString());
                LOGGER.info("Global Actuator States : " + ActuatorResponseHandler.getCurrentState().toString());
                time++;

            }
        }


}
