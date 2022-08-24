package steps;

import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import static org.junit.Assert.assertNotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.ReportExtender;
import utility.Log;

public class TestStepActions {	
	
/**
 	* Waits until a page is fully loaded or maxTimeInSeconds is reached.
 	* Creates a record in logger.
    * @param driver - [WebDriver] - current WebDriver.
	* @param maxTimeInSeconds - [int] - max. waiting time in seconds.
 	*
 	*/
    public void waitForFullPageLoad(WebDriver driver, int maxTimeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTimeInSeconds));
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
        	boolean result = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            	if (!result) {
            		Log.info("Wait for Webpage to Load... Maximum Waiting Time: " + String.valueOf(maxTimeInSeconds) +  " seconds.");
                }
        return result;
        });
    }

    public void clearAndSet(WebElement element, String value){
    	element.sendKeys(Keys.CONTROL, Keys.BACK_SPACE);
    	element.sendKeys(value);
		element.sendKeys(Keys.ENTER);
	}
/**
     * Scrolls specific element to view (top of screen).
     * Creates a record in logger.
     * @param driver - [WebDriver] - current WebDriver.
     * @param element - [WebElement] - web element specified by id/xpath path.
     * 
     */       
	public static void scrollElementIntoView(WebDriver driver, WebElement element) {
		Log.info("Scroll Element into View");
	    JavascriptExecutor javascript = (JavascriptExecutor)driver;
	    javascript.executeScript("arguments[0].scrollIntoView(true)", element);
	}
    
/**
 	* Scrolls specific element to view (middle of screen).
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] - web element specified by id/xpath path.
 	* 
 	*/	
    public void scrollElementIntoMiddleOfScreen(WebDriver driver, WebElement element) {
		Log.info("Scroll Element into Middle of Screen");
    	String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                                       + "var elementTop = arguments[0].getBoundingClientRect().top;"
                                       + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
    }
    
/**
 	* Scroll view to the bottom of the page.
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/			
    public void scrollPageIntoBottom(WebDriver driver) {
		Log.info("Scroll Page into Bottom");
    	Actions actions = new Actions(driver);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
    }
    
/**
 	* Sets focus to a specific element. If html/body is the element xpath, the whole page will be focused instead.
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] - web element specified by id/xpath path.
 	* 
 	*/        
    public void setFocusToElement(WebDriver driver, WebElement element) {
		Log.info("Set Focus to Element");
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        javascript.executeScript("arguments[0].focus();", element);
    }
    
/**
 	* Loses focus/blurs active element.
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    	
    public void blurFocusOfActiveElement(WebDriver driver) {
		Log.info("Blurr Focus of Active Element");
        ((JavascriptExecutor)driver).executeScript("!!document.activeElement ? document.activeElement.blur() : 0");
    }
    
/**
 	* Clicks on element.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] - web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/    
    public static void clickElement(WebElement element, String description) {
	    ReportExtender.generateLogAction(element, "clickElement", description);
	    element.click(); 
	}
    
/**
 	* DoubleClicks on element.
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] - web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/      
    public void doubleClickElement(WebDriver driver, WebElement element, String description) {
    	ReportExtender.generateLogAction(element, "doubleClickElement", description);
        assertNotNull(element);
        Actions action = new Actions(driver);
        action.doubleClick(element).perform();      
    }
	
/**
 	* Clicks on element using JavaScript.
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/        
    public void clickElementUsingJavascript(WebDriver driver, WebElement element, String description) {
    	ReportExtender.generateLogAction(element, "Click Element Using JavaScript", description);
        assertNotNull(element);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }
      
/**
 	* Clicks on element using Enter button.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/  
    public void clickElementUsingEnterButton(WebElement element, String description) {
    	ReportExtender.generateLogAction(element, "Click Element Using Enter", description);
        assertNotNull(element);
        element.sendKeys(Keys.ENTER);
    }
    
/**
 	* Scrolls and clicks on element using JavaScript and waits.
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @param timeInMilliseconds - [int] - wait time in milliseconds.
 	* 
 	*/        
    public void scrollAndClickElementUsingJavascriptAndWait(WebDriver driver, WebElement element, String description, int timeInMilliseconds) {
    	ReportExtender.generateLogWaitAction(element, "Scroll, Click Element Using JavaScript and Wait", description, timeInMilliseconds); 
    	assertNotNull(element);
        scrollElementIntoView(driver, element);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
        sleep(timeInMilliseconds);
    }
    
/**
 	* Clicks on element and waits.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @param timeInMilliseconds - [int] - wait time in milliseconds.
 	* 
 	*/        
    public void clickElementAndWait(WebElement element, String description, int timeInMilliseconds) {
    	ReportExtender.generateLogWaitAction(element, "Click Element and Wait", description, timeInMilliseconds);
        assertNotNull(element);
        element.click(); 
        sleep(timeInMilliseconds);
    }
    
/**
 	* Wait for element to be clickable
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param findBy - [By] - specified by xpath path.
 	* @param maxTimeInSeconds - [int] - max. waiting time in seconds.
 	* 
 	*/
    public void waitForElementClickable(WebDriver driver, By findBy, String description, int maxTimeInSeconds) {
    	ReportExtender.generateSimpleMaxTimeLog("Wait for Web Element to be Clickable.", description, maxTimeInSeconds);
    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
        		                  .withTimeout(Duration.ofSeconds(maxTimeInSeconds))
        		                  .pollingEvery(Duration.ofMillis(500))
    						      .ignoring(NoSuchElementException.class)
    						      .ignoring(StaleElementReferenceException.class)
    						      .ignoring(ElementNotInteractableException.class);
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
        	driver.findElement(findBy);
            return true;
        });
    }
    
/**
 	* Scroll and click on element.
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/      
    public void scrollAndClickElement(WebDriver driver, WebElement element, String description) {
    	ReportExtender.generateLogAction(element, "Scroll and Click Element", description);
        assertNotNull(element);
        scrollElementIntoView(driver, element);
        element.click(); 
    }
    
/**
 	* Scroll and click on element and wait.
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @param timeInMilliseconds - [int] - wait time in milliseconds.
 	* 
 	*/    
    public void scrollAndClickElementAndWait(WebDriver driver, WebElement element, String description, int timeInMilliseconds) {
    	ReportExtender.generateLogWaitAction(element, "Scroll and Click Element and Wait", description, timeInMilliseconds);
        assertNotNull(element);
        scrollElementIntoView(driver, element);
        element.click(); 
        sleep(timeInMilliseconds);
    }
    
/**
 	* Clicks on checkbox. Does nothing if the checkbox is already checked by default on the webpage. 
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/        
    public void checkCheckbox(WebElement element, String description) {
    	ReportExtender.generateLogAction(element, "Check Checkbox", description);
        assertNotNull(element);
        if (!element.isSelected()) {
        	element.click();    
        }
        else {
        	ReportExtender.logInfo("Checkbox element already checked on the webpage. No further action needed.");
        }   
    }
    
/**
 	* Unchecks a checkbox. Does nothing if the checkbox is already unchecked by default on the webpage. 
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/    	
    public void uncheckCheckbox(WebElement element, String description) {
    	ReportExtender.generateLogAction(element, "Uncheck Checkbox", description);
        assertNotNull(element);
        if (element.isSelected()) {
        	element.click();    
        }
        else {
        	ReportExtender.logInfo("Checkbox element already checked on the webpage. No further action needed.");
        }   
    }
    
/**
 	* Retrieves text of the element.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @return value - text of the element.
 	* 
 	*/        
    public String getElementText(WebElement element, String description) { 
    	assertNotNull(element);
        String value =  element.getAttribute("innerHTML"); 
        ReportExtender.generateLogFillAction(element, "Get Element Text", description, value);
        return value;
    }
    
/**
 	* Retrieves text visible on the webpage.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @return value - visible text of the element.
 	* 
 	*/        
    public String getDisplayedText(WebElement element, String description) { 
    	assertNotNull(element);
        String value =  element.getText(); 
        ReportExtender.generateLogFillAction(element, "Get Displayed Text", description, value);
        return value;
    }
    
/**
 	* Fills in an input field.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param value - [String] - text of the element that is going to be filled.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/	    
	public static void setElementText(WebElement element, String value, String description) {   
	    ReportExtender.generateLogFillAction(element, "Set Element Text", description, value);
	    assertNotNull(element);
	    element.clear(); 
	    element.sendKeys(value);  
	}
    
/**
 	* Fills in an input field and hits enter.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param value - [String] - text of the element that is going to be filled.
 	* @param description - [String] - description on the element (user friendly).
 	* 
	*/        
    public void setElementTextAndConfirm(WebElement element, String value, String description) {     
    	ReportExtender.generateLogFillAction(element, "Set Element Text and Confirm", description, value); 
        assertNotNull(element);
        element.clear(); 
        element.sendKeys(value);
        element.sendKeys(Keys.RETURN);
    }
    
/**
 	* Fills in an input field and waits for half second.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param value - [String] - text of the element that is going to be filled.
 	* @param description - [String] - description on the element (user friendly).
 	* @param timeInMilliseconds - [int] - wait time in milliseconds.
	* 
	*/        
    public void setElementTextAndWait(WebElement element, String value, String description, int timeInMilliseconds) {    
    	ReportExtender.generateLogFillWaitAction(element, "Set Element Text and Wait", description, value, timeInMilliseconds);   
    	assertNotNull(element);
        element.clear(); 
        element.sendKeys(value); 
        sleep(timeInMilliseconds);
    }
    
/**
 	* Fills in an input field, but masks it with "*" in logs/report. Used mainly for passwords.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param value - [String] - text of the element that is going to be filled.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/        
    public void setElementSecureText(WebElement element, String value, String description) { 
    	String secureText = value.replaceAll("(?s).", "*"); 
    	ReportExtender.generateLogFillAction(element, "Set Element Secure Text", description, secureText);
        assertNotNull(element);
        element.clear(); 
        element.sendKeys(value);
    }
    
/**
 	* Selects element from a specific visible text from dropdown list.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param value - [String] - text of the option that is going to be selected.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/    
    public void selectElementFromListByVisibleText(WebElement element, String value, String description) {
    	ReportExtender.generateLogFillAction(element, "Select Element from List by Visible Text", description, value);   
        assertNotNull(element);
        Select dropDown = new Select(element);
        dropDown.selectByVisibleText(value);
    }
    
/**
 	* Selects a value from a specific dropdown list.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param value - [String] - value from dropdown element that is going to be selected.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/       
    public void selectElementFromListByValue(WebElement element, String value, String description) {
    	ReportExtender.generateLogFillAction(element, "Select Element from List by Value", description, value);   
        assertNotNull(element);
        Select dropDown = new Select(element);
        dropDown.selectByValue(value);
    }
    
/**
 	* Selects a value from an old type of dropdown, that is actually values + links.
 	* Creates a record in logger and report.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param value - [String] - text of the option that is going to be selected.
 	* @param description - [String] - description on the element (user friendly).
 	* 
 	*/        
    public void selectElementFromButtonList(WebElement element, WebDriver driver, String value, String description) {
    	ReportExtender.generateLogFillAction(element, "Select Element from Button List", description, value);
        assertNotNull(element);
        WebElement dropdown = driver.findElement(By.linkText(value));
        dropdown.click();
    }
    
/**
 	* Retrieves preselected option from dropdown.
 	* Creates a record in logger.
 	* @param findBy - [By] - specified by xpath path.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param description - [String] - description on the element (user friendly).
 	* @return defaultItem - return preselected option.
 	* 
 	*/
    public String getPreselectedOption(By findBy, WebDriver driver, String description) {
    	Log.info("Get Preselected Option from Dropdown");
    	Select dropdown = new Select(driver.findElement(findBy));
        WebElement option = dropdown.getFirstSelectedOption();
        String defaultItem = option. getText();
        return defaultItem;
    }
    
/**
 	* Thread sleep action. Use only when no other wait is effective.
 	* @param timeInMilliseconds - [int] - wait time in milliseconds.
 	* 
 	*/        
    public void sleep(int timeInMilliseconds) {   
    	try {
    		Thread.sleep(timeInMilliseconds);
        }
        catch (Exception e) {
            ReportExtender.logFail("Method failed.");
            ReportExtender.logFail(e.toString());
        }
    }
    
/**
 	* Waits until an element appears on page or maxTimeInSeconds is reached.
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param findBy - [By] - specified by xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @param maxTimeInSeconds - [int] - max. waiting time in seconds.
 	* 
 	*/    
	public static void waitForElementVisible(WebDriver driver, By findBy, String description, int maxTimeInSeconds) {
	    ReportExtender.generateLogMaxTimeAction(findBy, "Waiting for Element Visible", description, maxTimeInSeconds);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTimeInSeconds));
	    wait.until((ExpectedCondition<Boolean>) driver1 -> {
	        boolean result = !driver.findElements(findBy).isEmpty();
	            if (!result) {
	                Log.info("Waiting for Element Visible...");
	            }
	        return result;
	    });
	}
	
/**
 	* Waits if element appears on page or maxTimeInSeconds is reached. If not it creates a log and the script continues.
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param findBy - [By] - specified by xpath path.
 	* @param maxTimeInSeconds - [int] - max. waiting time in seconds.
 	* @return true/false - return true/false option.
 	* 
 	*/ 	
	public boolean waitIfElementAppears(WebDriver driver, By findBy, String description, int maxTimeInSeconds ) {
		try {
			Log.info("Element: " + description);
			ReportExtender.generateSimpleMaxTimeLog("Waiting for web element to appear on webpage", description, maxTimeInSeconds);
	        
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTimeInSeconds));
	        wait.until((ExpectedCondition<Boolean>) driver1 -> {
	            boolean result = !driver.findElements(findBy).isEmpty();
	                if (!result) {
	                    Log.info("Waiting for web element to appear on webpage... Maximum waiting time: " + String.valueOf(maxTimeInSeconds) + " seconds.");
	                }
	            return result;
	        });
            return true;
		} 
		catch (TimeoutException ex) {
			ReportExtender.generateSimpleMaxTimeLog("Element didn't appear on webpage after desired time", description, maxTimeInSeconds);
			return false;
		}		
	}
	   
/**
 	* Refresh page.
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/
    public void refreshPage(WebDriver driver) {
    	Log.info("Refresh Page");
        driver.navigate().refresh();
    }
    
/**
 	* Waits until an element disappears from page or maxTimeInSeconds is reached.
 	* Creates a record in logger and report.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param findBy - [By] - specified by xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @param maxTimeInSeconds - [int] - max. waiting time in seconds.
 	* @return result - return only if element successfully disappear.
 	* 
 	*/    
    public void waitForElementDisappear(WebDriver driver, By findBy, String description, int maxTimeInSeconds) {
        Log.info("Waiting for web element to disappear from webpage.");
        Log.info("Element locator: " + findBy.toString());
        ReportExtender.generateLogMaxTimeAction(findBy, "Waiting for web element to disappear from webpage", description, maxTimeInSeconds);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTimeInSeconds));
        wait.until((ExpectedCondition<Boolean>) driver1 -> {
        boolean result = driver.findElements(findBy).isEmpty();
            if (!result) {
                Log.info("Waiting for web element to disappear from webpage...");
            }
            return result;
        });
    }
     
// Alert pop-up window actions:
/**
 	* Waits until an alert pop-up appears on page or maxTimeInSeconds is reached.
 	* Creates a record in logger and report. 
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param maxTimeInSeconds - [int] - max. waiting time in seconds.
 	* 
 	*/    
    public void waitForAlert(WebDriver driver, int maxTimeInSeconds) {
    	ReportExtender.generateSimpleMaxTimeLog("Wait For Alert", "Alert pop-up window", maxTimeInSeconds);   
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxTimeInSeconds));
        wait.until(ExpectedConditions.alertIsPresent());
    }

/**
 	* Accepts alert pop-up (clicks YES/OK/Accept/...).
 	* Creates a record in logger and report. 
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    
    public void acceptAlert(WebDriver driver) {
    	ReportExtender.generateSimpleLog("Accept Alert", "Accept Alert");   
        String parentWindowHandler = driver.getWindowHandle();
        driver.switchTo().alert().accept();
        driver.switchTo().window(parentWindowHandler);
    }

/**
 	* Switch to Alert.
 	* Creates a record in logger and report. 
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    
    public void switchToAlert(WebDriver driver) {
    	ReportExtender.generateSimpleLog("Switch to Alert", "Switch to Alert");   
        driver.switchTo().alert();
    }
    
/**
 	* Switch driver to parental window.
 	* Creates a record in logger and report. 
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    
    public void switchToParentalWindow(WebDriver driver) {
    	ReportExtender.generateSimpleLog("Switch to parental window", "Switch to parental window");
        String parentWindowHandler = driver.getWindowHandle();
        driver.switchTo().window(parentWindowHandler);
    }
    
/**
 	* Declines alert pop-up (clicks NO/Decline/Dismiss...).
 	* Creates a record in logger and report. 
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    
    public void dismissAlert(WebDriver driver) {
    	ReportExtender.generateSimpleLog("Dismiss Alert", "Dismiss Alert");
        String parentWindowHandler = driver.getWindowHandle();
        
        driver.switchTo().alert().dismiss();
        driver.switchTo().window(parentWindowHandler);
    }

/**
 	* Checks if a specific element is present on the page.
 	* Creates a record in logger. 
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param locator - [By] - specified by xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @return true - if element is present on the page / false - if element is not present on the page.
 	* 
 	*/    
    public boolean verifyElementIsPresent(WebDriver driver, By locator, String description) { 
        List<WebElement> pageElements = driver.findElements(locator);
        if (pageElements.size() != 0) {
            Log.info("Element is present on the webpage.");
            return true; 
        } 
        else {
            Log.info("Element is not present on the webpage.");
            return false; 
        }
    }
    
/**
 	* Checks if a specific element is selected.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param locator - [By] - specified by xpath path.
 	* @param description - [String] - description on the element (user friendly).
 	* @return true - if element is selected / false - if element is not selected.
 	* 
 	*/
    public boolean verifyIsSelected(WebDriver driver, By locator, String description) {
        if(driver.findElement(locator).isSelected()) {
        	Log.info("Element is Selected");
        	return true;
        }
        else {
        	Log.info("Element is not Selected");
        	return false;
        }
    }

	public boolean verifyAlzaIsChecked(WebDriver driver, By locator) {
		if(driver.findElement(locator).getAttribute("class").contains("checked")) {
			ReportExtender.logPass("Element is checked");
			return true;
		}
		else {
			ReportExtender.logWarning("Element not is checked");
			return false;
		}
	}

	public boolean verifyAlzaIsUnchecked(WebDriver driver, By locator) {
		if(!driver.findElement(locator).getAttribute("class").contains("checked")) {
			ReportExtender.logPass("Element is not checked");
			return true;
		}
		else {
			ReportExtender.logWarning("Element is checked");
			return false;
		}
	}



	/**
 	* Checks if a specific element has a specific text.
 	* Creates a record in logger and report. 
	* @param element - [WebElement] - web element we are checking.
 	* @param value - [String] - value that is expected to be found.
 	* @param description - [String] - description on the element (user friendly).
 	* @return true - if values match / false - if values do not match.
 	* 
 	*/    
    public boolean verifyText(WebElement element, String value, String description) {
    	ReportExtender.generateLogFillAction(element, "Verify Text", description, value);
        assertNotNull(element);
        String prefilledText = element.getAttribute("value");
        
        if (prefilledText.equals(value)) {  
            Log.info("Element text matches expected value.");
            ReportExtender.logInfo("Result........: Element text matches expected value.");
            return true; 
        } 
        else {
            Log.info("Element text does not match expected value.");
            ReportExtender.logInfo("Result........: Element text does not match expected value.");
            return false;
        } 
    }

/**
 	* Switches to frame by name.
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param frameName - [String] - name of the iFrame.
 	* 
 	*/	
    public void switchToFrameByName(WebDriver driver, String frameName) {
        try {
        	Log.info("Switch to frame: " + frameName);
            driver.switchTo().frame(frameName);
            printCurrentFrameName(driver);
        } 
        catch (NoSuchElementException exception) {
            throw exception;
        }
    }
    
/**
 	* Switches to frame by locator.
 	* Creates a record in logger. 
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param by - [By] - specified iFrame by xpath path.
 	* 
 	*/	
    public void switchToFrameByLocator(WebDriver driver, By by) {
        try {
        	Log.info("Switch to frame: " + by.toString());
            driver.switchTo().frame(driver.findElement(by));
            printCurrentFrameName(driver);
        } 
        catch (NoSuchElementException exception) {
            throw exception;
        }
    }
    
/**
 	* Print current frame name into logger
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/	
    public void printCurrentFrameName(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        String currentFrame = (String) jsExecutor.executeScript("return self.name");
        Log.info("Current Frame: " + currentFrame);
    }

/**
 	* Switches to parent Frame. Use multiple times in case of nested Frames.
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    
    public void switchToParentFrame(WebDriver driver) {
    	Log.info("Switch to Parent frame");
        driver.switchTo().parentFrame();
        printCurrentFrameName(driver);
    }
    
/**
 	* Switches out of the Frames.
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    
    public void stepOutOfAllFrames(WebDriver driver) {
    	Log.info("Step out of all Frames");
        driver.switchTo().defaultContent();
    }
    
/**
 	* Switches to another browser tab.
 	* Creates a record in logger and report. 
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param windowIndex - [int] - browser tab index counted from the left, starting with 1.
 	* 
 	*/    
    public void switchToBrowserTab(WebDriver driver, int windowIndex) {
    	ReportExtender.generateSimpleLog("Switch to Browser Tab", "Window index = " + windowIndex);
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(windowIndex - 1));
    }
    
/**
 	* Manually selects all and copies it to clipboard (CTRL + A, CTRL + C).
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    
    public void copyToClipboardManually(WebDriver driver) {
    	Log.info("Copy all to clipboard with keyboard");
        new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "a")).perform();
        sleep(1000);
        new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "c")).perform();
        sleep(1000);
    }
    
/**
     * Manually paste from clipboard (CTRL + V).
     * Creates a record in logger.
     * @param driver - [WebDriver] - current WebDriver.
     * 
     */    
    public void pasteFromClipboardManually(WebDriver driver) {
    	Log.info("Paste from clipboard with keyboard");
    	new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "v")).perform();
    	sleep(1000);
    }

/**
 	* Press key action.
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param key - [String] - if it is a standard key of alphabet/numeric, just send the key in param in quotation marks (e.g. key="t").
 	* Press SHIFT/TAB/ALT/CTRL/F1-12/ENTER keys
 	* @param key - [String] - start with Keys. + name of the key, no quotation marks (e.g. key=Keys.CONTROL).
 	*      
 	*/   
    public void pressKey(WebDriver driver, WebElement element, String key) {
    	Log.info("Press on keyboard: " + key);
        element.sendKeys(key);
    }

/**
 	* Press key action. 
 	* Creates a record in logger.
 	* @param driver - [WebDriver] - current WebDriver.
 	* @param element - [WebElement] -  web element specified by id/xpath path.
 	* @param key1 and key2 - [String] - if it is a standard key of alphabet/numeric, just send the key in param in quotation marks (e.g. key="t").
 	* Press SHIFT/TAB/ALT/CTRL/F1-12/ENTER keys
 	* @param key1 and key2 - [String] - start with Keys. + name of the key, no quotation marks (e.g. key=Keys.CONTROL).
 	*      
 	*/    
    public static void pressTwoKeysAtOnce(WebDriver driver, WebElement element, String key1, String key2) {
    	Log.info("Press on keyboard: " + key1 + ", " + key2);
        element.sendKeys(Keys.chord(key1, key2));
    }

/**
 	*  In case of multiple tabs/windows being open, this method moves focus to the last open window/tab.
 	*  Creates a record in logger.
 	*  @param driver - [WebDriver] - current WebDriver.
 	* 
 	*/    
    public void getLatestWindowFocused(WebDriver driver) {
        String mostRecentWindowHandle = "";
        Log.info("Focus on Latest Opened Window/Tab");
            for (String winHandle:driver.getWindowHandles()) {
                mostRecentWindowHandle = winHandle;        
            }
        driver.switchTo().window(mostRecentWindowHandle);
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        javascript.executeScript("window.focus();");
    }
    
/**
 	*  Hover Mouse Over Element and Click.
 	*  Creates a record in logger and report. 
 	*  @param driver - [WebDriver] - current WebDriver.
 	*  @param element - [WebElement] -  web element specified by id/xpath path.
 	*  @param description - [String] - description on the element (user friendly).
 	* 
 	*/ 
    public void mouseOverElementAndClick(WebDriver driver, WebElement element, String description) {
    	ReportExtender.generateLogAction(element, "Hover Mouse Over Element and Click", description);
    	assertNotNull(element);
    	Actions builder = new Actions(driver);
    	builder.moveToElement(element).click(element);
    	builder.perform();
    }

	public void mouseOverElement(WebDriver driver, WebElement element, String description) {
		ReportExtender.generateLogAction(element, "Hover Mouse Over Element", description);
		assertNotNull(element);
		Actions builder = new Actions(driver);
		builder.moveToElement(element);
		builder.perform();
	}

//TO-DO//    
/** 
 	* Retrieves value stored in session.
 	*  @param driver - [WebDriver] - current WebDriver.
 	*  @param key - [String] - session key, where the value is stored.
 	* 
 	*/    
    public String getSessionValue(WebDriver driver, String key) {
        JavascriptExecutor javascript = (JavascriptExecutor)driver;
        return (String) javascript.executeScript(String.format(
                    "return window.sessionStorage.getItem('%s');", key));
    }
}