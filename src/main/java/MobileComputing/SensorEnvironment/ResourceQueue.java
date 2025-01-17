package MobileComputing.SensorEnvironment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ResourceAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * CoAP Resource Class for responding to gateway
 * requests with appropriate messages and representations
 */
public class ResourceQueue extends CoapResource {

    protected final Logger LOGGER = LoggerFactory.getLogger(ResourceQueue.class.getCanonicalName());
    private resourceClass resourceType = resourceClass.UD;
    private interfaceClass interfaceType = interfaceClass.UD;
    private String locationId = "locn_0";
    private int timeStep = 0;
    private int smokeCount = 1;
    private boolean isGuidance = false;
    private boolean guidanceStatus = false;
    /**
     * File in which sensor data is stored as  representation
     * per line format
     */
    private String envParameter =   "EnvironmentData" + File.separator; ///Filename where Interface parameter representation values are stored


    /**
     * Creates a CoAP resource
     * @param resURI URI under which resource resides
     */
    public ResourceQueue(String resURI) {
        super(resURI);
    }

    public resourceClass getresourceType() {
        return resourceType;
    }

    public void setresourceType(resourceClass resourceType) {
        this.resourceType = resourceType;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public interfaceClass getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(interfaceClass interfaceType) {
        this.interfaceType = interfaceType;
        if(interfaceType.toString().equalsIgnoreCase(interfaceClass.GuidanceActuator.toString()))
        {
            this.isGuidance = true;
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Resource Id:" + super.getName())
                .append("Location Id:" + getLocationId() + "\n")
                .append("Resource Type:" + getresourceType() + "\n")
                .append("Interface Type:" + getInterfaceType() + "\n")
                .append("Environment File:" + getEnvParameter() + "\n").toString();
    }

    public String getEnvParameter() {
        return envParameter;
    }

    ///Mention Name of file inside InterfaceData folder
    public void setEnvParameter(String filename) {
        this.envParameter += filename;
        this.setLocationId(filename.substring(0, 6));
        LOGGER.info("Environment Parameter File for " + getLocationId() + " :: " + this.envParameter);
    }

    /**
     * Reads the representation of this resource from
     * an environment file associated with this resource
     * @return Representation of this resource
     * @throws IOException
     */
    private synchronized String readSingleValue() throws IOException {
        BufferedReader bufferedReader = null;
        String row = "";
        String temp = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(this.envParameter));

            int rowCount = 0;
            while (rowCount < this.timeStep) {
                row = bufferedReader.readLine();
				/*{
					System.out.println("sysout :"+rowCount);
					System.out.println("sysout :"+row);
				}*/
                rowCount++;
            }
            //temp = bufferedReader.readLine();
            //row = temp.split(",")[this.resourceType.getColVal()] ;
            row = bufferedReader.readLine().split(",")[this.resourceType.getColVal()];
            //LOGGER.info("temp : "+temp);
            //LOGGER.info("this.resourceType.getColVal() : "+this.resourceType.getColVal());

        } catch (FileNotFoundException fne) {
            LOGGER.error("Environment File Not found");
            LOGGER.error(fne.getMessage());
        } finally {
            if(this.timeStep < this.getnumLines() )
            {
                this.timeStep += 1;
            }

            bufferedReader.close();
           // LOGGER.info("Buffered Reader Closed");

        }
        return row;

    }

    /**
     * Used to find the last line of the environment file of
     * this resource when modifying it
     * @return the last line of the environment file
     */
    private synchronized String tail() {
        RandomAccessFile fileHandler = null;
        try {
            fileHandler = new RandomAccessFile(new File(this.envParameter), "r");
            long fileLength = fileHandler.length() - 1;
            StringBuilder sb = new StringBuilder();

            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();

                if (readByte == 0xA) {
                    if (filePointer == fileLength) {
                        continue;
                    }
                    break;

                } else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1) {
                        continue;
                    }
                    break;
                }

                sb.append((char) readByte);
            }

            String lastLine = sb.reverse().toString();
            return lastLine;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileHandler != null)
                try {
                    fileHandler.close();
                } catch (IOException e) {
                    /* ignore */
                }
        }
    }

    /**
     * Used to calculate the number of time steps
     * in the emulated sensor resource representation file
     * @return The number of lines in the environment file
     * @throws IOException
     */
    private int getnumLines() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.envParameter));
        int lines = -1;  ///To return a zero -based number of lines in file count
        while (reader.readLine() != null) lines++;
        reader.close();
        return  lines;
    }

    /**
     * Generates a sequence of representation values for an
     * actuator resource.
     * @param currVal Current value which has to be modified by
     *                logic newVal = currVal - (0.5)*val
     * @param val The value for scaling in computing logic
     * @return Representation of resource for modification to be used by an actuator
     */
    private synchronized String genVals(String[] currVal, String val) {
        String[] res = new String[3];
        Double d,dVal;
        for (int i = 0; i < currVal.length; i++) {
            res[i] = currVal[i];
        }
        for (resourceClass r : this.interfaceType.getModVars()) {
           if(r != resourceClass.smoke)
           {
               d = (Double.valueOf(currVal[r.getColVal()]) - (0.5 * Double.valueOf(val)));
               dVal = Math.floor(d * 100) / 100;
               String t = dVal.toString();
               res[r.getColVal()] = t;
           }
           else
           {
               if((smokeCount%10) == 0)
               {
               d = (Double.valueOf(currVal[r.getColVal()]) - (1* Double.valueOf(1)));
               dVal = Math.floor(d * 10) / 10;
               String t = dVal.toString();
               res[r.getColVal()] = t;
               }
               smokeCount += 1;
           }
        }
        return String.join(",", res);
    }

    /**
     * Changes representation of sensor resources ,
     * thereby acting as an actuator emulator
     * @param val Scaling value
     * @return Returns true if the operation was successful, else false
     * @throws IOException
     */
    private synchronized boolean writeSingleValue(String val) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(this.envParameter, true));
            String[] currVal = this.tail().split(",");
            String temp = this.genVals(currVal, val);
            //LOGGER.info("Inside writeSingleValue Writing : "+temp);
            bufferedWriter.write("\n" + temp);
            return true;
        } catch (FileNotFoundException fne) {
            LOGGER.error("Environment File Not found");
        } finally {
            bufferedWriter.close();
           // LOGGER.info("Buffered Writer Closed");
        }
        return false;
    }

    /*public void setIResGetHandler(iResGetHandler igh) {
        this.getHandler = igh;
    }*/

    /*
    CoAP GET Request Handler
     */
    @Override
    public void handleGET(CoapExchange exchange) {
        /*if (this.getObserverCount() == 0) {*///String ex = this.getHandler.handleGet();

            String ex = "";
            try {
                //LOGGER.info("Inside method handleGET");
                SensorState st = new SensorState();
                st.setParameter(this.resourceType.toString());
                st.setValue(this.readSingleValue());
                ex = st.serializedState();
            } catch (IOException ioe) {
                LOGGER.info(ioe.getMessage());
            }
            exchange.respond(ResponseCode.CONTENT, ex, MediaTypeRegistry.APPLICATION_JSON);
       /* } else {
            exchange.setMaxAge(this.updateInterval);
            exchange.respond(observerResult);
        }*/
    }


    /*
    CoAP POST Request Handler
     */
    @Override
    public void handlePOST(CoapExchange exchange) {
        if (!this.interfaceType.getModVars().isEmpty() && (! this.isGuidance))
        {
            String actuationCommand = exchange.getRequestText();
            try {
                ActuationCommand actCommand = new ActuationCommand();

                double kval = actCommand.deserializedCommand(actuationCommand).getVal();

                int knumTimes = actCommand.deserializedCommand(actuationCommand).getNumTimes();

                for (int i = 0; i < knumTimes; i++) {
                    this.writeSingleValue(String.valueOf(kval));
                }

            } catch (IOException ioe) {
                LOGGER.info(ioe.getMessage());
                exchange.respond("Actuation Failed Check Logs");
            }
            ///Ideally actuator response not required,so safe to remove the response but change in the end if necessary
            exchange.respond(ResponseCode.CHANGED,"{\"Response\" : \"Successful\"}",MediaTypeRegistry.APPLICATION_JSON);
        }
        else if((this.interfaceType.getModVars().isEmpty()) && (this.isGuidance))
        {
            this.guidanceStatus = Boolean.logicalXor(this.guidanceStatus,true);
            String res = "{\"Status\" : \""+this.guidanceStatus+"\"}";
            exchange.respond(ResponseCode.CHANGED,res,MediaTypeRegistry.APPLICATION_JSON);
            LOGGER.info("Guidance Actuator Command: "+exchange.getRequestText());
            LOGGER.info("Guidance Actuator Status: "+this.guidanceStatus);
        }
        //After JSON Unmarshalling will give two parameters:(String)kval,(int)knumTimes
        //this.postHandler.handlePost(kval, knumTimes);

    }

}
