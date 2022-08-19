package utility;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import runner.TestRunner;
import io.appium.java_client.windows.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

//Predefined webdriver creator class
public class BrowserFactory {
	
	static TestRunner TestRunner                               = new TestRunner();
	private static HashMap<String, Object> globalParametersMap = TestRunner.getGlobalParametersMap();
	private static String driverPath                           = ConfigFileReader.getWebDriverPath();
	private static String firefoxInstallPath                   = ConfigFileReader.getFirefoxInstallPath();
	
	//Call desired browser with predefined settings
	//Call browser with help of OpenBrowser class
	//Driver exec file is fetched from 'config.properties' via 'getDriverExecFilePath'
	//Driver exec files are different for different operating systems(windows, unix)
	public static void getBrowser(String browser) throws Exception {
		getOnlineDriverNoVersioning(browser);
		getBrowserSettings(browser);
	}
	
    public static void getBrowser(String browser, String driverMode, String driverVersion) throws Exception {
    	if(driverIsOnlineNoVersionSpecified(driverMode, driverVersion)) {
    		getOnlineDriverNoVersioning(browser);
    	}
    	if(driverIsOnlineVersionSpecified(driverMode, driverVersion)) {
    		getOnlineDriverWithVersioning(browser, driverVersion);
    	}
    	if(driverIsOffline(driverMode)) {
    		getOfflineDriver(browser);
    	}	
    	getBrowserSettings(browser);
	}
    
    private static boolean driverIsOnlineNoVersionSpecified(String driverMode, String driverVersion) {
    	return (driverMode.equalsIgnoreCase("-") || driverMode.equalsIgnoreCase("ONLINE")) && driverVersion.equalsIgnoreCase("-");
    }
    
    private static boolean driverIsOnlineVersionSpecified(String driverMode, String driverVersion) {
    	return !driverMode.equalsIgnoreCase("-") && driverMode.equalsIgnoreCase("ONLINE") && !driverVersion.equalsIgnoreCase("-");
    }
    
    private static boolean driverIsOffline(String driverMode) {
    	return !driverMode.equalsIgnoreCase("-") && driverMode.equalsIgnoreCase("OFFLINE");
    }
    
    private static void getOnlineDriverNoVersioning(String browser) throws Exception {
        switch (browser.toUpperCase()) {	
            case "CHROME":
            case "CHROME-HEADLESS":
            	WebDriverManager.chromedriver().setup();
		    break;
		
            case "FIREFOX":
            case "FIREFOX-HEADLESS":
            	WebDriverManager.firefoxdriver().setup();
		    break;
		
            case "IE":
            	WebDriverManager.iedriver().setup();
		    break;

            case "EDGE":
            case "EDGE-HEADLESS":
            	WebDriverManager.edgedriver().setup();
		    break;
		    
            case "OPERA":
            	WebDriverManager.operadriver().setup();
            break;
        }
    }
    
    private static void getOnlineDriverWithVersioning(String browser, String driverVersion) throws Exception {
        switch (browser.toUpperCase()) {	
            case "CHROME":
            case "CHROME-HEADLESS":
            	WebDriverManager.chromedriver().driverVersion(driverVersion).setup();
		    break;
		
            case "FIREFOX":
            case "FIREFOX-HEADLESS":
            	WebDriverManager.firefoxdriver().driverVersion(driverVersion).setup();
		    break;
		
            case "IE":
            	WebDriverManager.iedriver().driverVersion(driverVersion).setup();
		    break;

            case "EDGE":
            case "EDGE-HEADLESS":
            	WebDriverManager.edgedriver().driverVersion(driverVersion).setup();
		    break;
		    
            case "OPERA":
            	WebDriverManager.operadriver().driverVersion(driverVersion).setup();
            break;
        }
    }
    
    private static void getOfflineDriver(String browser) throws Exception {
        switch (browser.toUpperCase()) {
            case "CHROME":
            case "CHROME-HEADLESS":
		        System.setProperty("webdriver.chrome.driver", driverPath + ConfigFileReader.getDriverExecFilePath("chromedriverExecFile"));
		    break;
		
            case "FIREFOX":
            case "FIREFOX-HEADLESS":
        	    System.setProperty("webdriver.gecko.driver", driverPath + ConfigFileReader.getDriverExecFilePath("geckodriverExecFile"));
		    break;
		
            case "IE":
        	    System.setProperty("webdriver.ie.driver", driverPath + ConfigFileReader.getDriverExecFilePath("IEdriverExecFile"));
		    break;

            case "EDGE":
            case "EDGE-HEADLESS":
        	    System.setProperty("webdriver.edge.driver", driverPath + ConfigFileReader.getDriverExecFilePath("edgedriverExecFile"));
		    break;
		    
            case "OPERA":
            	System.setProperty("webdriver.opera.driver", driverPath + ConfigFileReader.getDriverExecFilePath("operadriverExecFile"));
            break;
        }
    }
    
    private static WebDriver getBrowserSettings(String browser) throws Exception {
        WebDriver driver = null;
        	
        //Chrome
        if(browser.equalsIgnoreCase("Chrome")) {
        	HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        	chromePrefs.put("profile.default_content_settings.popups", 0);
        	
        	ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            chromeOptions.addArguments("disable-extensions");
            chromeOptions.addArguments("--window-size=1920,1080");
            chromeOptions.addArguments("--disable-features=VizDisplayCompositor");
            chromeOptions.addArguments("--disable-notifications");
            
            driver = new ChromeDriver(chromeOptions);
        }  
        
        //Chrome-Headless
        if(browser.equalsIgnoreCase("Chrome-Headless")) {    	
        	HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        	chromePrefs.put("profile.default_content_settings.popups", 0);
        	
        	ChromeOptions headlessChromeOptions = new ChromeOptions();
        	headlessChromeOptions.setExperimentalOption("prefs", chromePrefs);
        	headlessChromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        	headlessChromeOptions.setCapability(ChromeOptions.CAPABILITY, headlessChromeOptions);
            headlessChromeOptions.addArguments("headless");
            headlessChromeOptions.addArguments("disable-extensions");
            headlessChromeOptions.addArguments("--window-size=1920,1080");
            headlessChromeOptions.addArguments("--disable-features=VizDisplayCompositor");
            headlessChromeOptions.addArguments("--disable-notifications");
            
            driver = new ChromeDriver(headlessChromeOptions);
        }  
                
        //Firefox
        if(browser.equalsIgnoreCase("Firefox")) {
            //If OS is not Windows - firefox.exe path is not needed
        	//As firefox.exe path is not part of 'config.properties' for macOS/Linux the 'firefoxInstallPath' is set to null, the 'webdriver.firefox.bin' not touched
        	if(!(firefoxInstallPath == null)) {
        	    System.setProperty("webdriver.firefox.bin", firefoxInstallPath);
        	}
                
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setAcceptInsecureCerts(true);
            firefoxOptions.addArguments("--marionette-port");
                
            driver = new FirefoxDriver(firefoxOptions);
        }
        
        //Firefox-Headless
        if(browser.equalsIgnoreCase("Firefox-Headless")) {
            //If OS is not Windows - firefox.exe path is not needed
        	//As firefox.exe path is not part of 'config.properties' for macOS/Linux the 'firefoxInstallPath' is set to null, the 'webdriver.firefox.bin' not touched
        	if(!(firefoxInstallPath == null)) {
        	    System.setProperty("webdriver.firefox.bin",  firefoxInstallPath);
        	}
        	
            FirefoxOptions headlessFirefoxOptions = new FirefoxOptions();
            headlessFirefoxOptions.setHeadless(true);
            headlessFirefoxOptions.setAcceptInsecureCerts(true);
            headlessFirefoxOptions.addArguments("--marionette-port");
                
            driver = new FirefoxDriver(headlessFirefoxOptions);
        }
        
        //Internet explorer
        if(browser.equalsIgnoreCase("IE")) {      	
        	InternetExplorerOptions ieOptions = new InternetExplorerOptions();
            ieOptions.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "about:blank");
            ieOptions.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
            ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

            driver = new InternetExplorerDriver(ieOptions);            
        }
        
        //Edge
        if(browser.equalsIgnoreCase("Edge")) {     	
        	EdgeOptions edgeOptions = new EdgeOptions();
        	edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        	edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        	     	
            driver = new EdgeDriver(edgeOptions);
        }
        
        //Edge-Headless
        if(browser.equalsIgnoreCase("Edge-Headless")) {        	
        	EdgeOptions edgeOptions = new EdgeOptions();
        	edgeOptions.addArguments("headless");
            edgeOptions.addArguments("disable-gpu");
        	edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        	edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        	     	
        	driver = new EdgeDriver(edgeOptions);
        }
        
        //Opera
        if(browser.equalsIgnoreCase("Opera")) {  	
        	OperaOptions operaOptions = new OperaOptions();
        	operaOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        	operaOptions.setCapability(OperaOptions.CAPABILITY, operaOptions);
        	operaOptions.addArguments("disable-extensions");
        	operaOptions.addArguments("--window-size=1920,1080");
        	operaOptions.addArguments("--disable-features=VizDisplayCompositor");
        	operaOptions.addArguments("--disable-notifications");
            
            driver = new OperaDriver(operaOptions);
        }    
        
        //Safari - macOS only 
        //Arguments not supported
        //SafariOptions - supported only one option, merging options not supported
        //To use multiple capabilities use DesiredCapabilities - tested via macOS Mojave
        //IDE can throw warning for driver instance
        if(browser.equalsIgnoreCase("Safari")) {
              	
        	DesiredCapabilities safariCapabilities = new DesiredCapabilities();
        	safariCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        	safariCapabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        	safariCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, true);
        	
            driver = new SafariDriver(safariCapabilities);
        }
            
        if(!browser.equalsIgnoreCase("Chrome") &&
           !browser.equalsIgnoreCase("Chrome-Headless") &&
           !browser.equalsIgnoreCase("Opera") &&
            browser != null) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }
                 
        globalParametersMap.put("driver", driver);
        return driver;
    }
                 
	//Get driver for BrowserStack
	public static void getBrowserStack(String browser, String browserVersion, String os, String osVersion, String device, String appiumVersion, String resolution) throws Exception {
        RemoteBrowserFactory.getBrowserStackDriver(browser, browserVersion, os, osVersion, device, appiumVersion, resolution);
        globalParametersMap.put("remoteDriver", "trueBrowserStack");
	}
	
	//Get driver for SauceLabs
	public static void getSauceLabs(String browser, String browserVersion, String os, String osVersion, String device, String appiumVersion, String resolution) throws Exception {
        RemoteBrowserFactory.getSauceLabsDriver(browser, browserVersion, os, osVersion, device, appiumVersion, resolution);
        globalParametersMap.put("remoteDriver", "trueSauceLabs");
	}
	
	//Get driver for Desktop application
	public static void getWinAppDriver(String execFileLocation) throws MalformedURLException {
		startWinAppDriverProcess();
		try {
		    DesiredCapabilities capabilities = new DesiredCapabilities();			
			capabilities.setCapability("app", execFileLocation);	
			@SuppressWarnings("rawtypes")
			WindowsDriver driver = new WindowsDriver(new URL("http://127.0.0.1:4723/"), capabilities);
			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			globalParametersMap.put("driver", driver);
		} 
		catch (Exception e) {
			Log.info("Unable to open application!");
		    e.printStackTrace();
	    }
    }
	
	private static void startWinAppDriverProcess() {
		 try {
			 Log.info("Starting WinAppDriver service");
	         String winAppDriverPath = ConfigFileReader.getWinAppDriverPath();
			 ProcessBuilder builder = new ProcessBuilder(winAppDriverPath).inheritIO();
			 Process winAppDriverProcess = builder.start();
			 globalParametersMap.put("winAppDriverProcess", winAppDriverProcess);
		} 
		catch (IOException e) {
		    Log.info("Unable to launch WinAppDriver!");
			e.printStackTrace();
		}
	}
	
	private static void stopWinAppDriverProcess() {
		Process winAppDriverProcess = (Process) globalParametersMap.get("winAppDriverProcess") != null ? (Process) globalParametersMap.get("winAppDriverProcess") : null;
		 if(winAppDriverProcess != null) {
			 Log.info("Stopping WinAppDriver service");
			 winAppDriverProcess.destroy();			 
		 }
	}
	
	//Close currently used web driver
	public static void closeBrowser(WebDriver driver) {
		try {
		    if(driver != null) {
			    driver.close();
			    //Firefox/Chrome webdriver bug workaround - MarionetteDriver#quit() => java.lang.IllegalStateException: UnixUtils may not be used on Windows #2701 
                Thread.sleep(3000);                
                //no exception thrown if driver is not running
		        driver.quit();
		    }
		}
		catch (Exception e) {}
		Log.info("Quitting driver");
		stopWinAppDriverProcess();
	}
	
	/*
	//Place following import between other import in case of use wininum
	import java.io.File;
	import org.openqa.selenium.winium.DesktopOptions;
	import org.openqa.selenium.winium.WiniumDriver;
	import org.openqa.selenium.winium.WiniumDriverService;
	
	//This method creates winium driver and puts it to globalParametersMap and defines needed service object on port 9999
	public static WiniumDriver getWinium(String applicationPath) throws Exception {
    System.setProperty("webdriver.winium.desktop.driver", driverPath + ConfigFileReader.getDriverExecFilePath("winiumdriverExecFile"));
    File execFilePath = new File(driverPath + ConfigFileReader.getDriverExecFilePath("winiumdriverExecFile"));
		
	DesktopOptions desktopOptions = new DesktopOptions();
	
	desktopOptions.setApplicationPath(applicationPath);
    
    WiniumDriverService service = new WiniumDriverService.Builder().usingDriverExecutable(execFilePath)
                                 .usingPort(9999).withVerbose(true).withSilent(false).buildDesktopService();
    startWiniumService(service);
   
	WiniumDriver driver = new WiniumDriver(service, desktopOptions); 
	globalParametersMap.put("driver", driver);
	globalParametersMap.put("service", service);
	
	return driver;
    }
	
    //Method to start needed service
	private static void startWiniumService(WiniumDriverService service) {
		try {
            service.start();
        } catch (Exception e) {
            System.out.println("Exception while starting WINIUM service");
            e.printStackTrace();
        }
	}
	
	//Method to stop used service
	private static void stopWiniumService(WiniumDriverService service) {
		try {
            service.stop();
        } catch (Exception e) {
            System.out.println("Exception while stopping WINIUM service");
            e.printStackTrace();
        }
	}
	
	//In order of use winium driver service needs to be stopped after the run. Please replace original closeBrowser method with following
	public static void closeBrowser(WebDriver driver) {
		try {
		    if(driver != null) {
		    	WiniumDriverService service = (WiniumDriverService)globalParametersMap.get("service");
		    	stopWiniumService(service);
			    driver.close();
                Thread.sleep(3000);
		        driver.quit();  
		    }
		}
		catch (Exception e) {}
		Log.info("Quitting driver");
	}*/
}