package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class configReader {
    private static Properties properties;
    static {
        try {
            FileInputStream file = new FileInputStream(
                    "src/main/resources/config.properties");

            properties = new Properties();
            properties.load(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String mobile){
       return properties.getProperty(mobile);
    }
}
