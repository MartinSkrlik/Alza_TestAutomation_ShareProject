package utility;

import static utility.Constant.InputKeys.*;
import static utility.Queries.Test_run.*;
import java.io.File;
import java.time.Instant;
import org.zeroturnaround.zip.ZipUtil;
import config.Config.Database;
import steps.Hooks;

public class AddRunToDatabase {

    public void setUpResultForDatabase() {
        System.setProperty(GLOBAL_RUN_ID.get(), "-");
        System.setProperty(RESULT_SWITCH.get(), (ConfigFileReader.getValueFromProperties("resultSwitch") != null ? ConfigFileReader.getValueFromProperties("resultSwitch") : "OFF"));   
        addGGlobalRunIdToSuite();
        if (isSendingDataToDatabase()) {
            addDataToTestRunTable();
        }
    }
    
    public void updateTestRun() {
        if (isSendingDataToDatabase()) {
        	Hooks hooks = new Hooks();
        	TestResultDTO testResults = hooks.getTestResultDTO();
    	    int scenariosTotal  = testResults.getScenariosTotal();
            int scenariosPassed = testResults.getScenariosPassed();
            int scenariosFailed = testResults.getScenariosFailed();
            DatabaseUtils db = new DatabaseUtils(Database.ROBO);
            db.insertQuery(String.format(UPDATE_RUN_STATUS.get(), 
            		scenariosTotal,
            		scenariosPassed,
            		scenariosFailed,
                    getRoborunId()));
            
            db.insertHtmlBlob(ADD_HTML_REPORT.get(), 
                    getRoborunId(), 
                    zipReport());
        }      
        System.setProperty(GLOBAL_RUN_ID.get(), "-");
    }
    
    private boolean isSendingDataToDatabase() {
        return System.getProperty(RESULT_SWITCH.get()).equalsIgnoreCase("ON");
    }
    
    private void addGGlobalRunIdToSuite() {
    	generateGlobalRunID();
    }
    
    private String generateGlobalRunID() {
        long unixTimestamp = Instant.now().toEpochMilli();
        String globalRunId = Long.toString(unixTimestamp).substring(2);

        System.setProperty(GLOBAL_RUN_ID.get(), globalRunId);
        Log.info("GLOBAL_RUN_ID: " + globalRunId);
        return globalRunId;
    }
    
    private void addDataToTestRunTable() {  
        DatabaseUtils db = new DatabaseUtils(Database.ROBO);
        db.insertQuery(String.format(INSERT_NEW_TESTRUN.get(), 
                getRoborunId(), 
                getTestset(), 
                System.getProperty("user.name"),
                "0"));     
    }
    
    private String getRoborunId() {
        return System.getProperty(GLOBAL_RUN_ID.get());
    }
    
    private String getTestset() {
        return System.getProperty(TESTSET.get());
    }
    
    private String zipReport() {
        String pathToOriginalFolder = "." + System.getProperty("scr.folder");
        String pathToZippedFolder   = pathToOriginalFolder + ".zip";
        ZipUtil.pack(new File(pathToOriginalFolder), new File(pathToZippedFolder));
        return pathToZippedFolder;
    }
}