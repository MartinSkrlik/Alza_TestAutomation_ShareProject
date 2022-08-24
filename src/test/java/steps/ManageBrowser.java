package steps;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import utility.BrowserFactory;
import utility.ConfigFileReader;
import utility.Log;
import utility.ReportExtender;
import runner.TestRunner;

public class ManageBrowser {	

	static TestRunner TestRunner = new TestRunner();
	private static HashMap<String, Object> globalParametersMap = TestRunner.getGlobalParametersMap();
	
    @Given("^Open browser \"([^\"]*)\"$")
    public void open_browser(String browser) throws Throwable {
    	try {
            BrowserFactory.getBrowser(browser);
    	}
        catch (Exception e) {
        	logError(e);
        }
    }
    
    @Given("^Open browser \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void open_browser(String browser, String driverMode, String driverVersion) throws Throwable {
    	try {
            BrowserFactory.getBrowser(browser, driverMode, driverVersion);
    	}
        catch (Exception e) {
        	logError(e);
        }
    }
    
	@Given("^Open browserstack \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void open_browserstack(String browser, String browserVersion, String os, String osVersion, String device, String appiumVersion, String resolution) throws Throwable {
		try {
            BrowserFactory.getBrowserStack(browser, browserVersion, os, osVersion, device, appiumVersion, resolution);
		}
        catch (Exception e) {
        	logError(e);
        }
    }
	
	@Given("^Open saucelabs \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void open_saucelabs(String browser, String browserVersion, String os, String osVersion, String device, String appiumVersion, String resolution) throws Throwable {
		try {
            BrowserFactory.getSauceLabs(browser, browserVersion, os, osVersion, device, appiumVersion, resolution);
		}
        catch (Exception e) {
        	logError(e);
        }
    }
	
	@When("Go to page \"([^\"]*)\"$")
	public void go_to_page(String page) {		
        WebDriver driver = (WebDriver)globalParametersMap.get("driver");
		try {
            driver.get(ConfigFileReader.getValueFromProperties(page));
            ReportExtender.logInfo(driver.getCurrentUrl());
	        ReportExtender.logScreen(driver);
		}
		catch (Exception e) {
			logPageError(e);
		}
	}

	@Given("^Open application \"([^\"]*)\"$")
    public void open_application(String application) throws Throwable {
		String execFileLocation = ConfigFileReader.getValueFromProperties(application);
    	try {
            BrowserFactory.getWinAppDriver(execFileLocation);
    	}
        catch (Exception e) {
        	Log.error("Unable to start WinAppDriver! " + e);
            ReportExtender.logFail("Unable to start WinAppDriver!");
        }
	}
	
	private void logError(Exception e) {
		Log.error("Unable to start Web driver! " + e);
        ReportExtender.logFail("Unable to start Web driver!");
	}
	
	private void logPageError(Exception e) {
		Log.error("Unable to load URL from property file! " + e);
		ReportExtender.logFail("Unable to load URL from property file! ");
	}
	
	/*
    //In order of use winium copy this step in to your steps class
    //Winium driver can be used with testStepActions as Winium driver can replace Web driver
    //Parameter needed - path of desired application
	@Given("^Open winium \"([^\"]*)\"$")
    public void open_winium(String execFileLocation) throws Throwable {
    	try {
            BrowserFactory.getWinium(execFileLocation);
    	}
        catch (Exception e) {
        	Log.error("Unable to start Winium driver! " + e);
            ReportExtender.logFail("Unable to start Winium driver!");
        }
	}*/
}