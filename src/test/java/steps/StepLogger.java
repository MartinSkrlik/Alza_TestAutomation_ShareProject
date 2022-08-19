package steps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import cucumber.api.PickleStepTestStep;
import cucumber.api.Result;
import cucumber.api.Scenario;
import cucumber.api.TestCase;
import cucumber.api.java.AfterStep;
import cucumber.api.java.BeforeStep;
import runner.TestRunner;
import utility.Log;
import utility.ReportExtender;

public class StepLogger {
	
	//Define index of first test step
	private int currentStepDefIndex                            = 0;
	
	static TestRunner TestRunner                               = new TestRunner();
	private static HashMap<String, Object> globalParametersMap = TestRunner.getGlobalParametersMap();

	//Read name of actual test step
    @BeforeStep
	public void beforeStep(Scenario scenario) throws Exception {

	    Field field = scenario.getClass().getDeclaredField("testCase");
	    field.setAccessible(true);
	    TestCase testCase = (TestCase) field.get(scenario);

	    List<PickleStepTestStep> stepDefs = testCase.getTestSteps()
	                                                .stream()
	                                                .filter(x -> x instanceof PickleStepTestStep)
	                                                .map(x -> (PickleStepTestStep) x)
	                                                .collect(Collectors.toList());
	    PickleStepTestStep currentStepDef = stepDefs.get(currentStepDefIndex);
	    
	    String step          = currentStepDef.getStepText().toString();
        String stepCorrected = (step.substring(0, 1).toUpperCase() + step.substring(1)).replaceAll("SECURE ([^ ]+)", "*****").replaceAll("_", " ");
        Log.stepInfo(stepCorrected);
        ReportExtender.logStepInfo(stepCorrected);
        globalParametersMap.put("currentStep", stepCorrected);
	}

    //Increase step index by 1 to get index of next step
    @AfterStep
	public void afterStep(Scenario scenario) {
	    currentStepDefIndex += 1;
	    logError(scenario);
	    globalParametersMap.put("currentStep", null);
	}
    
    //Log exception thrown
    private static void logError(Scenario scenario) {
    	try {
            Field field = scenario.getClass().getDeclaredField("stepResults");
    	    field.setAccessible(true);    
    	    ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
    	    for (Result result : results) {
    	        if (result.getError() != null) {
    	        	Log.stepError(globalParametersMap.get("currentStep").toString());
    	            ReportExtender.addCollapsibleException(globalParametersMap.get("currentStep").toString(), result.getError().toString());
    	            globalParametersMap.put("exceptionMessage", result.getError().toString().replaceAll("[^a-zA-Z]+", " "));
    	       }
    	    }
    	}
        catch (Exception e) {
    	       Log.info("Error while logging error");
    	}
    }
}