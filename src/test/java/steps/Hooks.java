package steps;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import runner.TestRunner;
import utility.BrowserFactory;
import utility.Log;
import utility.RemoteBrowserFactory;
import utility.ReportExtender;
import utility.TestResultDTO;

public class Hooks {
	
	private WebDriver driver;
	static TestResultDTO testResults = new TestResultDTO();
	static TestRunner TestRunner = new TestRunner();
	private static HashMap<String, Object> globalParametersMap = TestRunner.getGlobalParametersMap();
	
	public TestResultDTO getTestResultDTO() {
		return testResults;
	}
	
	//Start report of actual test case
    @Before
    public void startReportOfactualTC(Scenario scenario) {
    	Log.logStartOFTestCase(scenarioName(scenario));
    	cleanUpPreviousRun();
    	ReportExtender.startReportLogger(scenarioName(scenario), getFeatureFileNameFromScenarioId(scenario));
    }
    
    private String getFeatureFileNameFromScenarioId(Scenario scenario) {
    	String featureName = scenario.getId().replace("file:", "");
        return featureName;
    }
    
    //Close browser and finish report after actual test case
    @After
	public void finishTestCase(Scenario scenario) throws InterruptedException {
        driver = (WebDriver)globalParametersMap.get("driver");
        int scenariosTotal  = testResults.getScenariosTotal();
        int scenariosPassed = testResults.getScenariosPassed();
        int scenariosFailed = testResults.getScenariosFailed();
        
		if(scenario.isFailed() &&
		   !isRemoteRun(remoteRun())) {
		    Log.error("Scenario failed");
		    ReportExtender.logFail("Scenario failed");
		    ReportExtender.logScreen(driver);
		    scenariosFailed++;
		}
		
		if(!scenario.isFailed() &&
		   !isRemoteRun(remoteRun())) {
			ReportExtender.logPass("Scenario passed"); 
			scenariosPassed++;
		}
		
		if(scenario.isFailed() &&
	       isRemoteRun(remoteRun())) {
		    Log.error("Scenario failed");
			ReportExtender.logFail("Scenario failed");
			ReportExtender.logScreen(driver);
			RemoteBrowserFactory.markRemoteTestStatus("failed", exceptionMessage(), driver);	
			scenariosFailed++;
		}
		
	    if(!scenario.isFailed() &&
		   isRemoteRun(remoteRun())) {
		    ReportExtender.logPass("Scenario passed"); 
		    RemoteBrowserFactory.markRemoteTestStatus("passed", "Scenario passed", driver);
		    scenariosPassed++;
		}

		BrowserFactory.closeBrowser(driver);
		Log.logFinishOfTestCase(scenarioName(scenario));
	    ReportExtender.closeLogger();
	    scenariosTotal++;
	    
	    testResults.setScenariosTotal(scenariosTotal);
	    testResults.setScenariosPassed(scenariosPassed);
	    testResults.setScenariosFailed(scenariosFailed);
	}
    
    private String scenarioName(Scenario scenario) {
    	String scenarioName = scenario.getName();
    	globalParametersMap.put("scenarioName", scenarioName);
    	return scenarioName;
    }
    
    private boolean isRemoteRun(String remoteRun) {
		return remoteRun.contains("true");	
    }
    
    private String remoteRun() {
	    String remoteDriver = (String)globalParametersMap.get("remoteDriver") != null ? (String)globalParametersMap.get("remoteDriver") : "false";
	    return remoteDriver;
    }
    
    private String exceptionMessage() {
    	String exceptionMessage = (String)globalParametersMap.get("exceptionMessage") != null ? (String)globalParametersMap.get("exceptionMessage") : "Scenario failed";
		return exceptionMessage;
    }
    
    //Clean all parameters from global hash map before actual test case
    private void cleanUpPreviousRun() {
    	Log.info("Cleaning up data from previous scenario...");
    	globalParametersMap.clear();
    }
}