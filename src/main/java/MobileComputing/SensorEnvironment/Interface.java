package MobileComputing.SensorEnvironment;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Coap Server Utility class hosting a sensor or an actuator Coap resource
 */
public class Interface {

    private CoapServer coapServer;
    private final Logger LOGGER = LoggerFactory.getLogger(Interface.class.getCanonicalName());
    //private static int timeStep = 0;
    private static int interfaceIdGen = 0;
    private String interfaceId = "ep_";
    /**
     * Designated port on which CoAP server is supposed
     * to run:Default = 5683
     */
    private int portNumber = 5683;
    private String hostName = "localhost";
    private String ipAddress = "";
    private boolean isRunning = false;

    //private String envParameter = ""; ///Filename where Interface parameter representation values are stored


    public Interface(int portNumber) {
        this.portNumber = portNumber;
        interfaceIdGen += 1;
        this.interfaceId += interfaceIdGen;
        coapServer = new CoapServer(this.portNumber);

    }

    /**
     * Each CoAP server hosts a resource .A method to add a CoAP resource to a server
     * instance in a fluent API manner
     * @param coapResource CoAP  Resource which has to be added to the server
     * @return CoAP server to which the resource was added
     */
    public CoapServer addResource(CoapResource coapResource) {
        this.coapServer.add(coapResource);
        return this.coapServer;
    }

    /**
     * Start the CoAP server on the designated port.
     * Written in a fluent API
     * manner
     * @return The CoAP server instance
     */
    public boolean runInterface() {
        this.coapServer.start();
        LOGGER.info("Starting");
        LOGGER.info(this.toString());
        return true;
    }

    /**
     * Stops the  currently running CoAP server
     * @return CoAP server instance which was stopped
     */
    public boolean stopInterface() {
        this.coapServer.stop();
        LOGGER.info("Stopping");
        LOGGER.info(this.toString());
        LOGGER.info("Destroying Computation Resources");
        this.coapServer.destroy();
        LOGGER.info("Successfully Stopped the Coap Endpoint");
        return true;
    }

    public boolean getStatus() {
        this.isRunning = this.coapServer.getEndpoints().get(0).isStarted();
        return this.isRunning;
    }

    public String getInterfaceId() {
        return this.interfaceId;
    }

    public void setSensorId(String interface_id) {
        this.interfaceId = interface_id;
    }

    public CoapServer getCoapServer() {
        return this.coapServer;
    }

    public int getPortNumber() {
        if (this.getStatus())
            return this.coapServer.getEndpoints().get(0).getAddress().getPort();
        else
            return this.portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public String getHostName() {
        if (this.getStatus())
            return this.coapServer.getEndpoints().get(0).getAddress().getHostString();
        else
            return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIpAddress() {
        if (this.getStatus())
            return this.coapServer.getEndpoints().get(0).getAddress().getHostName();
        else
            return this.ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n**************************\n")
                .append("Interface Id=" + getInterfaceId() + "\n")
                .append("PortNumber:" + getPortNumber() + "\n")
                .append("HostName:" + getHostName() + "\n")
                .append("IP Address:" + getIpAddress() + "\n")
                .append("Status :" + getStatus() + "\n")
                .append("**************************\n");
        return sb.toString();
    }


}
