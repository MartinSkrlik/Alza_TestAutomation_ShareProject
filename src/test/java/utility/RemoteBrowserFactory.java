package utility;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import runner.TestRunner;

public class RemoteBrowserFactory {
	
	static TestRunner TestRunner = new TestRunner();
	private static HashMap<String, Object> globalParametersMap = TestRunner.getGlobalParametersMap();
	
	//Set capabilities for BrowserStack web driver
	public static void getBrowserStackDriver(String browser, String browserVersion, String os, String osVersion, String device, String appiumVersion, String resolution) throws Exception {
        WebDriver driver = null;       
        if(testRunsOnRemoteBrowserDesktopApp(device)) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browser", browser);
            capabilities.setCapability("browser_version", browserVersion);
            capabilities.setCapability("os", os);
            capabilities.setCapability("os_version", osVersion);
            capabilities.setCapability("resolution", resolution);
            capabilities.setCapability("browserstack.local", "false");
            
            driver = new RemoteWebDriver(new URL(Constant.BS_URL), capabilities);
            driver.manage().window().maximize();
        }
        else {
        	DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("os_version", osVersion);
            capabilities.setCapability("device", device);
            capabilities.setCapability("real_mobile", "true");
            capabilities.setCapability("browserstack.local", "false");
            
            driver = new RemoteWebDriver(new URL(Constant.BS_URL), capabilities);
        }
        globalParametersMap.put("driver", driver);
    }
	
	private static boolean testRunsOnRemoteBrowserDesktopApp(String device) {
		return device.equals("") ||
			   device.equals("-");
	}
	
	public static void getSauceLabsDriver(String browser, String browserVersion, String os, String osVersion, String device, String appiumVersion, String resolution) throws MalformedURLException {
		if(testRunsOnRemoteBrowserDesktopApp(device)) {
		    getSauceLabsDesktopDriver(browser, browserVersion, os, osVersion, device, appiumVersion, resolution);
		}
		else {
		    getSauceLabsMobileDriver(browser, browserVersion, os, osVersion, device, appiumVersion, resolution);
		}
	}
	
	//Set capabilities and option for SauceLabs desktop web driver
	private static void getSauceLabsDesktopDriver(String browser, String browserVersion, String os, String osVersion, String device, String appiumVersion, String resolution) throws MalformedURLException {
        WebDriver driver = null;
        
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("name", scenarioName());
        sauceOptions.setCapability("screenResolution", resolution);
        
        if(browser.equalsIgnoreCase("chrome")) {
        	ChromeOptions browserOptions = new ChromeOptions();
        	browserOptions.setCapability("platformName", os + " " + osVersion);
            browserOptions.setCapability("browserVersion", browserVersion);
            browserOptions.setCapability("sauce:options", sauceOptions);
            
            driver = new RemoteWebDriver(new URL(Constant.SL_URL), browserOptions);
        }
        if(browser.equalsIgnoreCase("edge")) {
        	EdgeOptions browserOptions = new EdgeOptions();
        	browserOptions.setCapability("platformName", os + " " + osVersion);
            browserOptions.setCapability("browserVersion", browserVersion);
            browserOptions.setCapability("sauce:options", sauceOptions);
            
            driver = new RemoteWebDriver(new URL(Constant.SL_URL), browserOptions);
        }
        if(browser.equalsIgnoreCase("firefox")) {
        	FirefoxOptions browserOptions = new FirefoxOptions();
        	browserOptions.setCapability("platformName", os + " " + osVersion);
            browserOptions.setCapability("browserVersion", browserVersion);
            browserOptions.setCapability("sauce:options", sauceOptions);
            
            driver = new RemoteWebDriver(new URL(Constant.SL_URL), browserOptions);
        }
        if(browser.equalsIgnoreCase("IE")) {
        	InternetExplorerOptions browserOptions = new InternetExplorerOptions();
        	browserOptions.setCapability("platformName", os + " " + osVersion);
            browserOptions.setCapability("browserVersion", browserVersion);
            browserOptions.setCapability("sauce:options", sauceOptions);
            
            driver = new RemoteWebDriver(new URL(Constant.SL_URL), browserOptions);
        }
        if(browser.equalsIgnoreCase("safari")) {
        	SafariOptions browserOptions = new SafariOptions();
        	browserOptions.setCapability("platformName", os + " " + osVersion);
            browserOptions.setCapability("browserVersion", browserVersion);
            browserOptions.setCapability("sauce:options", sauceOptions);
            
            driver = new RemoteWebDriver(new URL(Constant.SL_URL), browserOptions);
        }
        driver.manage().window().maximize();
        globalParametersMap.put("driver", driver);           
	}	
	
	//Set capabilities and option for SauceLabs mobile web driver
	private static void getSauceLabsMobileDriver(String browser, String browserVersion, String os, String osVersion, String device, String appiumVersion, String resolution) throws MalformedURLException {
        WebDriver driver = null;
        
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("name", scenarioName());
        
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("sauce:options", sauceOptions);
        capabilities.setCapability("sauce:appiumVersion", appiumVersion);
        capabilities.setCapability("appium:deviceName", device);
        capabilities.setCapability("appium:platformVersion", osVersion);
        capabilities.setCapability("platformName", os);
        capabilities.setCapability("browserName", browser);
            
        driver = new RemoteWebDriver(new URL(Constant.SL_URL), capabilities);
        globalParametersMap.put("driver", driver); 
        }
		
	//Send(mark) test status to remote browser testing tool
	public static void markRemoteTestStatus(String status, String reason, WebDriver driver) {	
		Log.info("Sending status" + status + "to remote browser testing tool");
		String remoteDriver = (String)globalParametersMap.get("remoteDriver");
		JavascriptExecutor javascript = (JavascriptExecutor)driver;
		if(remoteDriver.contains("BrowserStack")) {
			javascript.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""+ status +"\", \"reason\": \""+reason+"\"}}");
		}
		if(remoteDriver.contains("SauceLabs")) {
			javascript.executeScript("sauce:job-result=" + status);
		}
	}
	
	private static String scenarioName() {
	    String scenarioName = (String)globalParametersMap.get("scenarioName") != null ? (String)globalParametersMap.get("scenarioName") : "Scenario gurkensalat";
	    return scenarioName;
	}
}