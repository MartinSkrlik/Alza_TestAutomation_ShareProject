package utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import runner.TestRunner;
import com.relevantcodes.extentreports.ExtentReports;
import org.apache.commons.io.FileUtils;

public class ReportExtender {
	
	public static ExtentTest logger;
	public static ExtentReports report;
	public static File source;
	static TestRunner TestRunner        = new TestRunner();
	ReportNameCreator ReportNameCreator = new ReportNameCreator();
	
	public static String getTimeStamp(String timeFormat) {
		String timeStamp = new SimpleDateFormat(timeFormat).format(Calendar.getInstance().getTime()); 
		return timeStamp;		
	}	
	
	//Creation of the HTML report file and loading of the report configurations from resources/extentReportConfig.xml file
	public void startReport(String folder) {	
		String reportName = ReportNameCreator.createReportName();
		report = new ExtentReports(folder + "/" + reportName +".html", false);        
	    report.loadConfig(new File(ConfigFileReader.getReportConfigPath()));
	}
	
	//Generation of the log of the actual TC in the HTML report
	public static void startReportLogger(String scenarioName, String featureName) {
		logger = report.startTest("Scenario: " + scenarioName + ", Time Stamp: " + getTimeStamp("yyyy-MM-dd_HH-mm-ss"));
    	ReportExtender.logInfo("<span class='label blue'>Feature file:</span>" + "<span class='label'>" + featureName + "</span>");
	}
	
	public static void closeLogger() {
		report.endTest(logger);
	}
	
	public static void closeReport() {
        report.flush();
	}
		
	//Predefined methods for reporting, the methods can be called at any time, they will create a record in the report
	public static void logInfo(String message) {
		logger.log(LogStatus.INFO, message);
	}
	
	public static void logPass(String message) {
		logger.log(LogStatus.PASS, message);
	}
	
	public static void logWarning(String message) {
		logger.log(LogStatus.WARNING, addMarkup("WARN") + message);
	}
	
	public static void logFail(String message) {
        logger.log(LogStatus.FAIL, addMarkup("FAIL") + message);
    }
	
    public static void logStepInfo(String message) {
        ReportExtender.logger.log(LogStatus.INFO, addMarkup("NEXTSTEP") + "<span class='label blue'>" + message + "</span> ");
    } 
    
    public static String addMarkup(String value) {
        String textColour = "white-text";
        Labels enumValue = Labels.valueOf(value);
        return "<span class='label " + textColour + " " + enumValue.colour + "'>" + enumValue.msg + "</span> ";
    }
    
    enum Labels {
        PASS ("green",    "SUCCESS:"),
        WARN("orange",    "WARNING:"),
        FAIL("red",       "FAILURE:"),
        ERROR("red",      "ERROR:"),
        INFO("blue",      "INFO:"),
    	NEXTSTEP("blue",  "STEP:"),
    	STEPFAILED("red", "FAILED STEP:");
        
        private String colour;
        private String msg;
        
        Labels(String colour, String msg) {
            this.colour = colour;
            this.msg = msg;
        }
        
        public String getLabelColour() {
            return colour;
        }
        
        public String getMsg() {
            return msg;
        }
    }
    
	public static void logScreen(WebDriver driver) {
		if(driver != null) {
			String screenshotName = "Screenshot_" + getTimeStamp("yyyy-MM-dd_HH-mm-ss-SSS") + ".png";
			takeScreenshot(driver, screenshotName);
			addScreenshotToReport(screenshotName);
		}	
	}
	
	private static void takeScreenshot(WebDriver driver, String screenshotName) {
		try {
            source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    		String relativeFolderPath = "." + System.getProperty("scr.folder");
            FileUtils.copyFile(source, new File (relativeFolderPath + "/" + screenshotName));
        } 
        catch (Exception e) {
            e.printStackTrace(); 
        } 
	}
	
	private static void addScreenshotToReport(String screenshotName) {
		String image = logger.addScreenCapture(getRelativePathOfFile(screenshotName).toString());
        ReportExtender.logger.log(LogStatus.INFO, "Screenshot: " + image);
	}
	
	private static Path getRelativePathOfFile(String screenshotName) {	 
        String basePath   = System.getProperty("scr.folder");
        Path pathAbsolute = Paths.get(basePath + "/" + screenshotName);
        Path pathBasic    = Paths.get(basePath);
        Path pathRelative = pathBasic.relativize(pathAbsolute);

        return pathRelative;
    }
	
	public static String getPathOfWorkspace() {
        String workspacePath = new File("").getAbsolutePath();
        return workspacePath;
    }
	
    public static void generateLogAction(WebElement element, String action, String description) {	    
    	Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Log.info("Action.....: " + action);
        Log.info("Description: " + description);
        Log.info("Locator....: " + element); 
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");

        String header = action + " : " + description;

        ReportExtender.logger.log(LogStatus.INFO, "<div class=\"collapsible_acc\">" + header + "</div>"
                                  + "<div class=\"content\" style=\"display: none;\">"
                                  + "<p>Action........:   " + action + "</p><p>Description:   " + description +"</p><p>Locator.......:   " 
                                  + element + "</p></div>");
        
    }
    
    public static void generateLogWaitAction(WebElement element, String action, String description, int timeInMilliseconds) {
    	String timeInMillisecondsString = String.valueOf(timeInMilliseconds/1000) + " seconds";
    	
    	Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Log.info("Action.....: " + action);
        Log.info("Description: " + description);
        Log.info("Locator....: " + element); 
        Log.info("Wait time:.: " + timeInMillisecondsString);
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");

        String header = action + " : " + description;

        ReportExtender.logger.log(LogStatus.INFO, "<div class=\"collapsible_acc\">" + header + "</div>"
                                  + "<div class=\"content\" style=\"display: none;\">"
                                  + "<p>Action........:   " + action + "</p><p>Description:   " + description +"</p><p>Locator.......:   " 
                                  + element + "</p><p>Wait time..:   " + timeInMillisecondsString + "</p></div>");
        
    }
    
    public static void generateLogMaxTimeAction(By findBy, String action, String description, int maxTimeInSeconds) {
    	String maxTimeInSecondsString = String.valueOf(maxTimeInSeconds) + " seconds";
    	
    	Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Log.info("Action..............: " + action);
        Log.info("Description.........: " + description);
        Log.info("Locator.............: " + findBy.toString());
        Log.info("Maximum Waiting Time: " + maxTimeInSecondsString);
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");

        String header = action + " : " + description;

        ReportExtender.logger.log(LogStatus.INFO, "<div class=\"collapsible_acc\">" + header + "</div>"
                                  + "<div class=\"content\" style=\"display: none;\">"
                                  + "<p>Action.............................:   " + action + "</p><p>Description....................:   " 
                                  + description +"</p><p>Locator...........................:   " 
                                  + findBy + "</p><p>Maximum Waiting Time:   " + maxTimeInSecondsString + "</p></div>");
    }
    
    public static void generateLogFillAction(WebElement element, String action, String description, String value) {
    	Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Log.info("Action.....: " + action);
        Log.info("Description: " + description);
        Log.info("Locator....: " + element);
        Log.info("Value......: " + value);
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");

        String header = action + " : " + description;
        
        ReportExtender.logger.log(LogStatus.INFO, "<div class=\"collapsible_acc\">" + header + "</div>"
                                  + "<div class=\"content\" style=\"display: none;\">"
                                  + "<p>Action........:   " + action + "</p><p>Description:   " + description + "</p><p>Locator.......:   "
                                  + element + "</p><p>Value.........:   " + value + "</p></div>");
    }
    
    public static void generateLogFillWaitAction(WebElement element, String action, String description, String value, int timeInMilliseconds) {
    	String timeInMillisecondsString = String.valueOf(timeInMilliseconds/1000) + " seconds";
    	
    	Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Log.info("Action.....: " + action);
        Log.info("Description: " + description);
        Log.info("Locator....: " + element);
        Log.info("Value......: " + value);
        Log.info("Wait time:.: " + timeInMillisecondsString);
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");

        String header = action + " : " + description;
        
        ReportExtender.logger.log(LogStatus.INFO, "<div class=\"collapsible_acc\">" + header + "</div>"
                                  + "<div class=\"content\" style=\"display: none;\">"
                                  + "<p>Action........:   " + action + "</p><p>Description:   " + description + "</p><p>Locator.......:   "
                                  + element + "</p><p>Value.........:   " + value + "</p><p>Wait time..:   " + timeInMillisecondsString + "</p></div>");
    }
    
    public static void generateSimpleLog(String action, String description) {
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Log.info("Action.....: " + action);
        Log.info("Description: " + description);
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");

        String header = action + " : " + description;

        ReportExtender.logger.log(LogStatus.INFO, "<div class=\"collapsible_acc\">" + header + "</div>"
                                  + "<div class=\"content\" style=\"display: none;\">"
                                  + "<p>Action........:   " + action + "</p><p>Description:   " + description + "</p></div>");
    }
    
    public static void generateSimpleMaxTimeLog(String action, String description, int maxTimeInSeconds) {
    	String maxTimeInSecondsString = String.valueOf(maxTimeInSeconds) + " seconds";
    	
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");
        Log.info("Action..............: " + action);
        Log.info("Description.........: " + description);
        Log.info("Maximum Waiting Time: " + maxTimeInSecondsString);
        Log.info("------------------------------------------------------------------------------------------------------------------------------------------------------");

        String header = action + " : " + description;

        ReportExtender.logger.log(LogStatus.INFO, "<div class=\"collapsible_acc\">" + header + "</div>"
                                  + "<div class=\"content\" style=\"display: none;\">"
                                  + "<p>Action.................:   " + action + "</p><p>Description.........:   " + description + "</p><p>Maximum Waiting Time:   " + maxTimeInSecondsString + "</p></div>");
    }
    
    public static void addCollapsibleException(String header, String errorMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<div class=\"collapsible_acc\">" + addMarkup("STEPFAILED") + "<span class='label red'>" 
                             + header + "</span></div><div class=\"content\" style=\"display: none;\">");
        stringBuilder.append("<pre>" + errorMessage + "</pre>");
        stringBuilder.append("</div>");
        ReportExtender.logger.log(LogStatus.ERROR, stringBuilder.toString());
    }
    
    public static void addListToCollapsibleLog(String header, LogStatus logStatus, ArrayList<?> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(COLLAPSIBLE_HEADER, header));
        
        for (Object param : list) {
            stringBuilder.append(String.format(COLLAPSIBLE_MONOSPACE_VALUES, param));
        }
        
        stringBuilder.append(DIV_END);
        ReportExtender.logger.log(logStatus, stringBuilder.toString());
    }
    
    public static void logListInput(Map<String, Object> input, String header) {
        ArrayList<String> list        = new ArrayList<String>();
        ArrayList<Integer> lenghtList = new ArrayList<Integer>();
        
        input.entrySet().forEach
        (entry -> {lenghtList.add(entry.getKey().length());});
        int expectedLength = Collections.max(lenghtList);
        Log.info("=======================================================");
        Log.info(header);
        input.entrySet().forEach
            (entry -> {list.add(setDesiredLengthOfString(entry.getKey(), expectedLength) + ": " + entry.getValue()); 
                       Log.info(setDesiredLengthOfString(entry.getKey(), expectedLength) + ": " + entry.getValue());});
        Log.info("=======================================================");
        ReportExtender.addListToCollapsibleLog(header, LogStatus.INFO, list);
    }
    
    private static String setDesiredLengthOfString(String stringToFormat, Integer expectedLenght) {
    	int length = stringToFormat.length();
    	for(;length < expectedLenght; length++) {
    		stringToFormat = stringToFormat + ".";
    	}
    	return stringToFormat;	
    }
    
    private static final String COLLAPSIBLE_HEADER           = "<div class=\"collapsible_acc\">%s</div><div class=\"content\" style=\"display: none;\">";
    private static final String COLLAPSIBLE_MONOSPACE_VALUES = "<p style=\"font-family: monospace;\">%s</p>";
    private static final String DIV_END                      = "</div>";
}