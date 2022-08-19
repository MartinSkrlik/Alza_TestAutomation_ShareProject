package steps;

import org.openqa.selenium.WebDriver;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import runner.TestRunner;
import utility.Validation;
import utility.Log;
import utility.ReportExtender;
import utility.SAUCEDEMO_PageMapper;
import static page.SAUCEDEMO_LoginPage.loginPageItems.*;
import java.util.HashMap;
import page.SAUCEDEMO_LoginPage;
public class SAUCEDEMO_LoginSteps extends TestStepActions {
	
	static TestRunner TestRunner = new TestRunner();
	private static HashMap<String, Object> globalParametersMap = TestRunner.getGlobalParametersMap();
	private WebDriver driver = (WebDriver)globalParametersMap.get("driver");

    @When("^Login user with username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void login_user_with_username_and_password(String username, String password) throws Throwable {
    	setElementText(UsernameElement.getElement(driver), username, UsernameElement.getDescription());
        setElementText(PasswordElement.getElement(driver), password, PasswordElement.getDescription());
        ReportExtender.logScreen(driver);
    	clickElement(LoginButton.getElement(driver), LoginButton.getDescription());
    }
    
    @When("^Login user with username SECURE \"([^\"]*)\" and password SECURE \"([^\"]*)\"$")
    public void login_user_with_username_secure_and_password_secure(String username, String password) throws Throwable {
    	setElementSecureText(UsernameElement.getElement(driver), username, UsernameElement.getDescription());
    	setElementSecureText(PasswordElement.getElement(driver), password, PasswordElement.getDescription());
    	ReportExtender.logScreen(driver);    
        waitForElementClickable(driver, LoginButton.getLocator(), LoginButton.getDescription(), 15);
    	clickElement(LoginButton.getElement(driver), LoginButton.getDescription());
    }
    
    @Then("Verify page title \"([^\"]*)\" is visible")
    public void verify_page_title_is_visible(String title) {
    	waitForElementVisible(driver, PageTitle.getLocator(), PageTitle.getDescription(), 60);
    	new Validation("PAGE TITLE", getElementText(PageTitle.getElement(driver), PageTitle.getDescription()), title).stringEquals();
    	ReportExtender.logScreen(driver);
    }

	@Then("^Verify page title is not visible$")
    public void verify_page_title_is_not_visible() throws Throwable {
    	if(driver.findElements(PageTitle.getLocator()).size() > 0) {
    		Log.info(SAUCEDEMO_PageMapper.getFailMessage("ONE"));
            ReportExtender.logPass("Validating " + PageTitle.getDescription() + ": </br>" + ReportExtender.addMarkup("FAIL") + SAUCEDEMO_PageMapper.getFailMessage("ONE"));
    	}
    	else{
    		Log.info(SAUCEDEMO_PageMapper.SUCCESS_MESSAGE);
            ReportExtender.logPass("Validating " + PageTitle.getDescription() + ": </br>" + ReportExtender.addMarkup("PASS") + SAUCEDEMO_PageMapper.SUCCESS_MESSAGE);
    	}
    }

	//in the following method you can find example how to correctly call dynamic xpath from the correct page(this example uses LoginPage.java) class
    @Then("^Verify error message is visible$")
    public void verify_error_message_is_visible() throws Throwable {
    	SAUCEDEMO_LoginPage page = new SAUCEDEMO_LoginPage(driver);
    	
    	waitForElementVisible(driver, page.getErrorMessageLocator("error"), ErrorMessage.getDescription(), 60);
    	new Validation("ERROR MESSAGE", getDisplayedText(page.getErrorMessageElement("error"), ErrorMessage.getDescription()), SAUCEDEMO_PageMapper.ERROR_MESSAGE).stringEquals();
    	Thread.sleep(1000);
    	ReportExtender.logScreen(driver);
    }
}