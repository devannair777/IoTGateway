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

public class ResourceQueue extends CoapResource {

    protected final Logger LOGGER = LoggerFactory.getLogger(ResourceQueue.class.getCanonicalName());
    /*private iResGetHandler getHandler;
    private iResPostHandler postHandler;*/
    private resourceClass resourceType = resourceClass.UD;
    private interfaceClass interfaceType = interfaceClass.UD;
    private String locationId = "locn_0";
    private int timeStep = 0;
    //private int maxTime = 8;   ///0 - based so 1 less than number of lines in Environment file
    private boolean isGuidance = false;
    private boolean guidanceStatus = false;
    private String envParameter =   "EnvironmentData" + File.separator; ///Filename where Interface parameter representation values are stored

    /*private Timer obsTimer;
    private int updateInterval = 1000;
    private boolean isObservable = false;
    private volatile String observerResult = "";
    private volatile String prevObserverResult = "";*/

    public ResourceQueue(String resURI) {
        super(resURI);
    }

    /*public ResourceQueue(String resURI, boolean isObs) {
        super(resURI);
        obsTimer = new Timer();
        this.isObservable = isObs;
        this.setObservable(isObs);
        this.setObserveType(CoAP.Type.CON);
        obsTimer.schedule(new ObserveTask(), 0, this.updateInterval);
    }*/

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


    public synchronized String readSingleValue() throws IOException {
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
            LOGGER.info("Buffered Reader Closed");

        }
        return row;

    }

    public synchronized String tail() {
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

    private int getnumLines() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.envParameter));
        int lines = -1;  ///To return a zero -based number of lines in file count
        while (reader.readLine() != null) lines++;
        reader.close();
        return  lines;
    }

    private synchronized String genVals(String[] currVal, String val) {
        String[] res = new String[4];
        Double d,dVal;
        for (int i = 0; i < currVal.length; i++) {
            res[i] = currVal[i];
        }
        for (resourceClass r : this.interfaceType.getModVars()) {
            d = (Double.valueOf(currVal[r.getColVal()]) - (0.1 * Double.valueOf(val)));
            dVal = Math.floor(d * 100) / 100;
            String t = dVal.toString();
            //res[r.getColVal()] = t.substring(0,4);
            res[r.getColVal()] = t;
        }
        return String.join(",", res);
    }

    public synchronized boolean writeSingleValue(String val) throws IOException {
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
            LOGGER.info("Buffered Writer Closed");
        }
        return false;
    }

    /*public void setIResGetHandler(iResGetHandler igh) {
        this.getHandler = igh;
    }*/

    @Override
    public void handleGET(CoapExchange exchange) {
        /*if (this.getObserverCount() == 0) {*///String ex = this.getHandler.handleGet();

            String ex = "";
            try {
                LOGGER.info("Inside method handleGET");
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
            exchange.respond(ResponseCode.CONTENT,"Successful",MediaTypeRegistry.APPLICATION_JSON);
        }
        else if((this.interfaceType.getModVars().isEmpty()) && (this.isGuidance))
        {
            this.guidanceStatus = Boolean.logicalXor(this.guidanceStatus,true);
            exchange.respond(ResponseCode.CONTENT,"Status:"+this.guidanceStatus,MediaTypeRegistry.APPLICATION_JSON);
            LOGGER.info("Guidance Actuator Command: "+exchange.getRequestText());
            LOGGER.info("Guidance Actuator Status: "+this.guidanceStatus);
        }
        //After JSON Unmarshalling will give two parameters:(String)kval,(int)knumTimes
        //this.postHandler.handlePost(kval, knumTimes);

    }

    /*public int showNumObsRelations() {
        String res = Integer.valueOf(this.getObserverCount()).toString();
        LOGGER.info("Number of Observers to resource : " + super.getName() + " : " + res);
        return this.getObserverCount();
    }*/

    /*public void setPostHandler(iResPostHandler postHandler) {
        this.postHandler = postHandler;
    }*/

    /*private class ObserveTask extends TimerTask {
        String currVal = "";

        @Override
        public void run() {

            //String ex = "";
            try {
                SensorState st = new SensorState();
                st.setParameter(resourceType.toString());
                currVal = readSingleValue();
                st.setValue(currVal);
                observerResult = st.serializedState();
                if (!currVal.equals(prevObserverResult)) {
                    changed();
                }
                prevObserverResult = currVal;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }*/
}
