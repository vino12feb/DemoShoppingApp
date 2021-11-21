package utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import runner.RunDemoTest;

public class ConfigFileReader {

    private Properties properties;
    private final String propertyFilePath= RunDemoTest.configFile;

    public ConfigFileReader(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public String getConfig(String key){
        String configValue = properties.getProperty(key);
        if(configValue!= null) return configValue;
        else throw new RuntimeException(key + " is not specified in the configuration.properties file");
    }

}
