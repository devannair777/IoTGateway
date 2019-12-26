package MobileComputing.Simulation;

import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class ProductionEnvSetup
{
    public ProductionEnvSetup(){}

    public static void setEnvironment() throws IOException {

        String res = "";
        String[] filenames = {"locn_1.txt","locn_2.txt","locn_3.txt"};
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        String appDir = new File("EnvironmentData").getAbsolutePath();
        File f = new File(appDir);
        if(!f.exists())
        {
            f.mkdir();
        }
        //String filename = "locn_1.txt";
        for(String filename : filenames)
        {
            InputStream resource = new ClassPathResource(
                    "static" + File.separator + "InterfaceData" + File.separator + filename).getInputStream();
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(resource));
                bufferedWriter = new BufferedWriter(new FileWriter(appDir + File.separator + filename, false));
                String temp = "";
                int count = 0;
                while ((temp = bufferedReader.readLine()) != null)
                {
                    if (count != 0) {
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.write(temp);
                    res = res + temp + "\n";
                    count += 1;
                }

                //System.out.println(res);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                bufferedWriter.close();
                bufferedReader.close();
            }
        }
    }


}
