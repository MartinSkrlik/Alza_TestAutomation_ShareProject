package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import page.AlzaPage;
import runner.TestRunner;
import utility.ReportExtender;
import utility.Validation;

import static page.AlzaPage.alzaItems.*;
import java.util.HashMap;
import java.util.List;

public class AlzaSteps extends TestStepActions {
    static runner.TestRunner TestRunner = new TestRunner();
    private static HashMap<String, Object> globalParametersMap = TestRunner.getGlobalParametersMap();
    private WebDriver driver = (WebDriver)globalParametersMap.get("driver");

    AlzaPage page = new AlzaPage(driver);
    String itemsPreFiltering = "";
    String itemsAfterFilteringBrand = "";
    String itemsAfterallfilter = "";

    @And("Remember Item Count before filtering")
    public void rememberItemCountBeforeFiltering() {
        itemsPreFiltering = getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription());
    }

    @And("Set price range between {string} to {string}")
    public void setPriceRangeBetweenTo(String arg0, String arg1) {
        waitForElementVisible(driver, PriceMinInput.getLocator(), PriceMinInput.getDescription(), 20);
        clearAndSet(PriceMinInput.getElement(driver), arg0);
        waitForElementVisible(driver, PriceMaxInput.getLocator(), PriceMaxInput.getDescription(), 20);
        clearAndSet(PriceMaxInput.getElement(driver), arg1);
        sleep(10000);
    }

    @And("Search {string}")
    public void search(String arg0) {
        if (waitIfElementAppears(driver, CookieAcceptButton.getLocator(), CookieAcceptButton.getDescription(), 20)){
            clickElement(CookieAcceptButton.getElement(driver), CookieAcceptButton.getDescription());
        }
        waitForElementVisible(driver, SearchBar.getLocator(), SearchBar.getDescription(), 20);
        setElementText(SearchBar.getElement(driver), arg0, SearchBar.getDescription());
        clickElement(SearchButton.getElement(driver), SearchButton.getDescription());
    }

    @And("Verify {string} is visible")
    public void verifyIsVisible(String arg0) {
    }


    @Then("Remember Item Count after price filter")
    public void rememberItemCountAfterPriceFilter() {
    }

    @Then("Click checkbox {string}")
    public void click_Checkbox(String value) {
        checkCheckbox(page.getCheckBoxElement(value), "CLICK ON CHECKBOX");
        waitForElementVisible(driver, page.getstatusElementLocator(value),"CHECKBOX IS VISIBLE",10 );
        sleep(2000);
        new Validation("checkbox is selected", verifyIsSelected(driver, page.checkInputElementLocator(value), value)).isTrue();
    }

    @Then("Remember Item Count after brand filter")
    public void rememberItemCountAfterBrandFilter() {
        itemsAfterFilteringBrand = getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription());
    }

    @Then("Remember Item Count after all filters")
    public void rememberItemCountAfterAllFilters() {
        itemsAfterallfilter = getElementText(ItemCountAfterTwoFIlter.getElement(driver), ItemCountAfterTwoFIlter.getDescription());
        ReportExtender.logInfo(itemsAfterallfilter);

    }

    @Then("Switch to tab {string}")
    public void switchToTab(String arg0) {

    }

    @Then("Verify first item price between {string} and {string}")
    public void verifyFirstItemPriceBetweenAnd(String arg0, String arg1) {
    }

    @Then("Verify product description contains {string}")
    public void verifyProductDescriptionContains(String checkbox) {
        scrollElementIntoMiddleOfScreen(driver, BrandsFilterGroup.getElement(driver));
        List<WebElement> elements = driver.findElements(Description.getLocator());
        for (WebElement element : elements) {
            new Validation("DESCRIPTION", element.getText(), checkbox).contains();
        }
        sleep(1000);
        ReportExtender.logScreen(driver);
    }

    @Then("Uncheck checkbox {string}")
    public void uncheckCheckbox(String value) {
        checkCheckbox(page.getCheckBoxElement(value), "UNCHECK CHECKBOX");
        waitForElementVisible(driver, page.getUncheckElementLocator(value),"CHECKBOX IS UNCHECKED",10 );
        sleep(2000);
    }

    @Then("Verify item count after uncheck item filter")
    public void verifyItemCountAfterUncheckItemFilter() {
    }

    @Then("Verify item count after uncheck brand filter")
    public void verifyItemCountAfterUncheckBrandFilter() {
    }

    @Then("Clear parameters")
    public void clearParameters() {
    clickElement(ClearSelectedParameters.getElement(driver), ClearSelectedParameters.getDescription());
    sleep(2000);
    waitForElementVisible(driver, TextForVerification.getLocator(), TextForVerification.getDescription(), 10);
    }

    @Then("Verify item count after filter clearing")
    public void verifyItemCountAfterFilterClearing() {
    }
}
