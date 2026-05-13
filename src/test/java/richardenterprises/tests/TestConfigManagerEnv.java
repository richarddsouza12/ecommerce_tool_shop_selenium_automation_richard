package richardenterprises.tests;

import org.testng.annotations.Test;
import richardenterprises.recources.ConfigManager;

public class TestConfigManagerEnv {


    @Test(groups = ("@SystemEnvCheck"))
    public void TestEnv() {

        ConfigManager configManager = ConfigManager.createOrGetInstance();
        System.out.println( "configManager.getBaseUrl()" );
        System.out.println( configManager.getBaseUrl() );
        System.out.println( "configManager.getConfiguredEnv()" );
        System.out.println( configManager.getConfiguredEnv() );
        System.out.println( "configManager.getEnvVariableByName(admin_email)");
        System.out.println( configManager.getEnvVariableByName("admin_email") );

    }



}
