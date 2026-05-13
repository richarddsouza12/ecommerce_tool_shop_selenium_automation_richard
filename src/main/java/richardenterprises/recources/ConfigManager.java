package richardenterprises.recources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {

    public static ConfigManager configManager;
    public Properties properties;

    public ConfigManager() {

        try {
            this.loadGlobalPropertiesFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ConfigManager createOrGetInstance() {
        if( configManager == null ) {
            configManager = new ConfigManager();
        }
        return configManager;
    }

    public String getBaseUrl() {

        return this.getEnvVariableByName("base_url");
    }

    public  String getConfiguredEnv() {

        String env_name = ( System.getProperty("env") != null ) ? System.getProperty("env") : this.properties.getProperty("env");
        return env_name;
    }


    public String getEnvVariableByName( String name) {

        String env_var_name_with_env_prefix = this.getConfiguredEnv() + "_" + name;
        return this.properties.getProperty( env_var_name_with_env_prefix );
    }

    public void loadGlobalPropertiesFile() throws IOException {

        Properties properties = new Properties();

        FileInputStream fileInputStream = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/java/richardenterprises/recources/GolbalData.properties" );
        properties.load( fileInputStream );

        this.properties = properties;

    }

}
