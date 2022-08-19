package utility;

public class Constant {
	
	//BrowserStack constants
    public static final String BS_USERNAME     = ConfigFileReader.getValueFromProperties("bs_username");
	public static final String BS_AUTOMATE_KEY = ConfigFileReader.getValueFromProperties("bs_automate_key");
	public static final String BS_URL          = "https://" + BS_USERNAME + ":" + BS_AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
	
	//SauceLabs constants
    public static final String SL_USERNAME     = ConfigFileReader.getValueFromProperties("sl_username");
	public static final String SL_AUTOMATE_KEY = ConfigFileReader.getValueFromProperties("sl_automate_key");
	public static final String SL_URL          = "https://" + SL_USERNAME + ":" + SL_AUTOMATE_KEY + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";
	
	public enum InputKeys {
    	PORTAL("portal"), ENVIRONMENT("environment"), BROWSER("browser"), DRIVER_MODE("driverMode"), DRIVER_VERSION("driverVersion"),
        FLOW("flow"), TEST_CASE_NR("testCaseNumber"), DRIVER("driver"), LINK("link"), RESULT_SWITCH("resultSwitch"),
        STEP_SWITCH("stepSwitch"), TESTSET("testsetID"),GLOBAL_RUN_ID("globalRunId"), GLOBAL_RESULT_ID("global_result_id"), ALL_STEPS("allSteps"),
        ;

        private String value;

        private InputKeys(String value) {
            this.value = value;
        }

        public String get() {
           return value;
        }
    }
}