package MobileComputing.SensorEnvironment.Test;

import MobileComputing.SensorEnvironment.ObservableResourceQueue;
import MobileComputing.SensorEnvironment.SpawnElements;
import MobileComputing.SensorEnvironment.resourceClass;

public class ObserverResourceTest
{
    public static void main(String[] args) {
        ObservableResourceQueue ob = SpawnElements.spawnObservableResource
                ("obs","obsv_1.txt", resourceClass.smoke);
        SpawnElements.spawnObservableSensor(5673,ob);
        ObserverGateway gw = new ObserverGateway();
        gw.discover();
        gw.execTask();;

    }
}
