package MobileComputing.Simulation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "MobileComputing.IoTGateway.Dashboard")
public class IotserviceApplication {

    public static void main(String[] args)
    {
       try
        {
            ProductionEnvSetup.setEnvironment(args);
            SpringApplication.run(IotserviceApplication.class, args);
            SensorEnvTest.EnvSetup();
        }

         catch (Exception e)
         {
            e.printStackTrace();
         }
    }

}
