package MobileComputing.SensorEnvironment;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ObservableResourceQueue extends ResourceQueue {

    private resourceClass resourceType = resourceClass.UD;

    private Timer obsTimer;

    public resourceClass getResourceType() {
        return resourceType;
    }

    public void setResourceType(resourceClass resourceType) {
        this.resourceType = resourceType;
    }

    private int updateInterval = 1000;
    private int timeStep = 0;
    private volatile String observerResult = "";
    private volatile String prevObserverResult = "";

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public ObservableResourceQueue(String resUri)
    {
        super(resUri);
        obsTimer = new Timer();

        this.setObservable(true);
        this.setObserveType(CoAP.Type.CON);
        obsTimer.schedule(new ObserveTask(), 0, this.updateInterval);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public synchronized String readSingleValue() throws IOException {
        BufferedReader bufferedReader = null;
        String row = "";
        String temp = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(super.getEnvParameter()));

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
            //LOGGER.info("Buffered Reader Closed");

        }
        return row;

    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(this.updateInterval*10);
        exchange.respond(CoAP.ResponseCode.CONTENT,observerResult, MediaTypeRegistry.APPLICATION_JSON);
    }

    private int getnumLines() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(super.getEnvParameter()));
        int lines = -1;  ///To return a zero -based number of lines in file count
        while (reader.readLine() != null) lines++;
        reader.close();
        return  lines;
    }

    public int showNumObsRelations() {
        String res = Integer.valueOf(this.getObserverCount()).toString();
        LOGGER.info("Number of Observers to resource : " + super.getName() + " : " + res);
        return this.getObserverCount();
    }

    private class ObserveTask extends TimerTask {
        String currVal = "";

        @Override
        public void run() {
            //LOGGER.info("Inside Method run() ");
            //String ex = "";
            try {
                SensorState st = new SensorState();
                st.setParameter(resourceType.toString());
                currVal = readSingleValue();
                st.setValue(currVal);
                observerResult = st.serializedState();
                //LOGGER.info("Previous Observer Result : "+prevObserverResult);
                //LOGGER.info("Observer Result : "+observerResult);
                if (!currVal.equals(prevObserverResult)) {
                    changed();
                }
                prevObserverResult = currVal;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
