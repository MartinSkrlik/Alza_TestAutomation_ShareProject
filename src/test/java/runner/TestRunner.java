package runner;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import utility.AddRunToDatabase;
import utility.ConfigFileReader;
import utility.ReportExtender;
import utility.ReportNameCreator;
import utility.Log;

@RunWith(Cucumber.class)
//Options used in test run
@CucumberOptions(features   =  "src/test/java/features/",
                 tags       = {"@AlzaFilterProducts"},
                 glue       = {"steps"},
                 plugin     = {"pretty"},
                 monochrome = true)

//Main class for test execution
public class TestRunner {
	
	//Initialization of global hash map to store test execution parameters
	private static Map<String, Object> globalParametersMap = new LinkedHashMap<String, Object>();
	//Create report name based on features and tags
	static ReportNameCreator reportNameCreator             = new ReportNameCreator();
	
	public HashMap<String, Object> getGlobalParametersMap() {
        return (HashMap<String, Object>) globalParametersMap;
   }
	
	//Set up of test report before run
	@BeforeClass
	public static void setup() {
		ReportExtender reportExtender = new ReportExtender();
		reportExtender.startReport(createReportFolder());
		AddRunToDatabase addRunToDatabase = new AddRunToDatabase();
		addRunToDatabase.setUpResultForDatabase();
	}

	//Create folder for reporting
	public static String createReportFolder() { 
		//Create report folder name for particular run
		String folderName         = "/" + ConfigFileReader.getReportFolderPath() + reportNameCreator.createReportName();
		//Create report folder name for particular run relative to project folder
        String relativeFolderPath = "." + folderName;               
        new File(relativeFolderPath).mkdir();
        System.setProperty("scr.folder", folderName);
        Log.info("Path to report: " + relativeFolderPath);
        return relativeFolderPath;
    }
	
	//Close report for particular run
	@AfterClass
	public static void writeExtentReport() {
	    ReportExtender.closeReport();
	    AddRunToDatabase addRunToDatabase = new AddRunToDatabase();
		addRunToDatabase.updateTestRun();
	}
}