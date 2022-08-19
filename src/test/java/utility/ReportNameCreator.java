package utility;

import java.util.List;
import static utility.Constant.InputKeys.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import cucumber.api.CucumberOptions;
import runner.TestRunner;

//Creator of the name of report folder and report file.
//It will collect the tags used in testRunner file or in CMD
public class ReportNameCreator {

	private List<String> tagsList;
	private List<String> featuresList;
	
	static TestRunner TestRunner = new TestRunner();
	
	//Create report name from time stamp, features and tags
    public String createReportName() {
		String reportName = ReportExtender.getTimeStamp("yyyy-MM-dd_HH-mm-ss") + "_Report_" + featuresNamePuller() + oneLinedTagsNamePuller();
		System.setProperty(TESTSET.get(), reportName);
		return reportName;
    }		
	
    //Fetch feature file names
    //AllFeature if not defined
	public String featuresNamePuller() {
		String[] featuresAsList = getFeaturesFromTestRunner(TestRunner.class);
		this.featuresList       = Arrays.asList(featuresAsList);
		List<String> listOfFeatures = new ArrayList<String>();
		for (String featuresToAdd : featuresList) {
			listOfFeatures.add(featuresToAdd.toString().replaceAll(ConfigFileReader.getFeatureFilesPath(), ""));
		}
		String listOfFeaturesCorrected = listOfFeatures.toString().replaceAll("\\[|\\]", "").replaceAll("\\s", "");
		if(listOfFeaturesCorrected.equals("")) {
			listOfFeaturesCorrected = "AllFeatures";
		}	
		return listOfFeaturesCorrected;
	}	
	
	//Fetch tag names
    //AllTags if not defined
	public String oneLinedTagsNamePuller() {
		String[] tagsAsList = getTags(TestRunner.class);
		this.tagsList       = Arrays.asList(tagsAsList);
		List<String> listOfTags = new ArrayList<String>();
		for (String tagsToAdd : tagsList) {
			listOfTags.add("_" + tagsToAdd.toString());
		}
		
		String oneLinedTagsName = listOfTags.toString().replaceAll("\\[|\\]", "").replaceAll("\\s", "").replaceAll(",", "");
		if(oneLinedTagsName.equals("_")) {
			oneLinedTagsName = "AllTags";
		}
		return oneLinedTagsName;
	}
	
    public String[] getTags(Class<?> classOfCucumberOptions) {
        String[] tags = this.getTagsFromCmdParams();
        if(tags.length > 0) {
            return tags;
        }
        return getTagsFromTestRunner(classOfCucumberOptions);
    }	
    
    private String[] getTagsFromTestRunner(Class<?> classOfCucumberOptions) {
        CucumberOptions cucumberOptions = classOfCucumberOptions.getAnnotation(CucumberOptions.class);
        String[] tags = cucumberOptions.tags();
        if(tags.length > 0 && tags[0].contains(",")) {
            return tags[0].split(",");
        }
        if(tags.length > 0 && tags[0].toLowerCase().contains(" or ")) {
            return tags[0].split("or");
        }
        if(tags.length > 0 && tags[0].toLowerCase().contains(" and ")) {
            return tags[0].split("and");
        }
        if(tags.length > 0 && !tags[0].contains(" or ") && !tags[0].toLowerCase().contains(" and ") && !tags[0].toLowerCase().contains(",")) {
            return tags;
        }
        return  new String[]{};
    }    
    
    private String[] getTagsFromCmdParams() {
        String proptag = System.getProperty("cucumber.options");
        
        if(proptag != null && proptag.length() > 0) {
            Pattern pattern   = Pattern.compile("--tags (@[^ ]+(,@[^ ]+)*)");
            Matcher matcher   = pattern.matcher(proptag);
            boolean condition = matcher.matches();
            if(condition && matcher.groupCount() >= 2 ) {
                String test = matcher.group(1);
                if(test != null && test.length() > 0) {
                    String[] bits = test.split(",");
                    if(bits.length > 0) {
                        return bits;
                    }
                }
            }
        }
        return  new String[]{};
    }
    
    private String[] getFeaturesFromTestRunner(Class<?> classOfCucumberOptions) {
        CucumberOptions cucumberOptions = classOfCucumberOptions.getAnnotation(CucumberOptions.class);
        String[] features = cucumberOptions.features();
        if(features.length > 0) {
            return features;
        }
        return  new String[]{};
    }    
}