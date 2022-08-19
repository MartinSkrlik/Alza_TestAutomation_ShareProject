package utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Configuration console logger
//Log format can be found in log4j2.xml file
public class Log {
	
	private static Logger Log = LogManager.getLogger(Log.class.getName());
	
	//Log start of actual TC. By default it is called in Hooks @Before
    public static void logStartOFTestCase(String scenarioName) {
    	Log.info("----------------------------------------------------------------------------------------");
        Log.info("----------------------------------------STARTED-----------------------------------------");
        Log.info("====================             " + scenarioName + "       ============================");
        Log.info("----------------------------------------------------------------------------------------");
        Log.info("----------------------------------------------------------------------------------------");
    }
    
    //Log finish of actual TC. By default it is called in Hooks @After
    public static void logFinishOfTestCase(String scenarioName) {
        Log.info("XXXXXXXXXXXXXXXXXXXXXXX             " +  "FINISHED" + "          XXXXXXXXXXXXXXXXXXXXXXX");
        Log.info("XXXXXXXXXXXXXXXXXXXXXXX             " + scenarioName + "         XXXXXXXXXXXXXXXXXXXXXXX");
        Log.info("X");
        Log.info("X");
        Log.info("X");
        Log.info("X");
    }
    
	//Callable log methods - generate log in console
	public static void info(String message) {
        Log.info(message);
    }
	 
    public static void warn(String message) {
        Log.warn(message);
    }
	 
    public static void error(String message) {
        Log.error(message);
    }
	 
    public static void fatal(String message) {
        Log.fatal(message);
    }
	 
    public static void debug(String message) {
        Log.debug(message);
    }
    
    public static void stepInfo(String message) {
        Log.info("STEP: " + message);
    }
    
    public static void stepError(String message) {
        Log.error("FAILED STEP: " + message);
    }
}