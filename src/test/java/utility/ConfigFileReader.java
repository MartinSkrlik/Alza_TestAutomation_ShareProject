package utility;

import java.util.Properties;
import java.io.File;
import java.io.InputStream;

public class ConfigFileReader {
	private static Properties properties = new Properties();  
	private static String propertyFilePath = "config.properties";
	
	//Get any value from config.properties file by it's key
    public static String loadValueFromPropertiesFile(String key) {
        String value = "";
        
        try {
        	loadPropertiesFile(propertyFilePath);
            value = properties.getProperty(key);
        } 
        catch (Exception e) {
        	System.out.println("config.properties file can not be loaded");
            e.printStackTrace();
        }
        return value;
    }
    
    //Read property file
    private static void loadPropertiesFile(String propertyFilePath) {
        try {
            InputStream input = ConfigFileReader.class.getClassLoader()
                               .getResourceAsStream(propertyFilePath);
            properties.load(input);
        }
        catch (Exception e) {
        	System.out.println("config.properties file can not be loaded");
            e.printStackTrace();
        }
    }
	
	public static String getValueFromProperties(String key) {
	    return loadValueFromPropertiesFile(key);
    }
	
	public static String getReportConfigPath() {
		String path = new File("").getAbsolutePath() + "/" +loadValueFromPropertiesFile("reportConfigPath");
	    return path;
    }
	
	public static String getReportFolderPath() {
		String path = loadValueFromPropertiesFile("reportFolderPath");
	    return path;
    }
	
	public static String getWebDriverPath() {
		String path = loadValueFromPropertiesFile("driverPath");
	    return path;
    }
	
	public static String getDriverExecFilePath(String driverName) throws Exception {
		String path = loadValueFromPropertiesFile(driverName);
	    if(path == null) {
	    	Log.error("Browser not supported");
		    ReportExtender.logFail("Browser not supported");
	    	throw new Exception("Browser not supported");	
	    }
		return path;
    }
	
	//Only for windows, geckodriver is unable to find firefox.exe on it's own
	public static String getFirefoxInstallPath() {
		String path = loadValueFromPropertiesFile("FirefoxPath");
	    return path;
    }
	
	public static String getFeatureFilesPath() {
		String path = loadValueFromPropertiesFile("featureFilesPath");
	    return path;
	}
	
	public static String getWinAppDriverPath() {
		String path = loadValueFromPropertiesFile("winAppDriverExecFile");
	    return path;
	}
}