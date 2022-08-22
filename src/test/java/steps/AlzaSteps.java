package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.WebDriver;
import page.AlzaPage;
import runner.TestRunner;
import static page.AlzaPage.alzaItems.*;
import java.util.HashMap;

public class AlzaSteps extends TestStepActions {
    static runner.TestRunner TestRunner = new TestRunner();
    private static HashMap<String, Object> globalParametersMap = TestRunner.getGlobalParametersMap();
    private WebDriver driver = (WebDriver)globalParametersMap.get("driver");

    AlzaPage page = new AlzaPage(driver);
    String itemsPreFiltering = "";




    @And("Search for {string}")
    public void search_for(String value) {
        if (waitIfElementAppears(driver, CookieAcceptButton.getLocator(), CookieAcceptButton.getDescription(), 20)){
            clickElement(CookieAcceptButton.getElement(driver), CookieAcceptButton.getDescription());
        }
        waitForElementVisible(driver, SearchBar.getLocator(), SearchBar.getDescription(), 20);
        setElementText(SearchBar.getElement(driver), value, SearchBar.getDescription());
        clickElement(SearchButton.getElement(driver), SearchButton.getDescription());
    }

    @And("Remember Item Count before filtering")
    public void rememberItemCountBeforeFiltering() {
        itemsPreFiltering = getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription());
    }

    @And("Set price range between {string} to {string}")
    public void setPriceRangeBetweenTo(String arg0, String arg1) {
        waitForElementVisible(driver, PriceMinInput.getLocator(), PriceMinInput.getDescription(), 20);
        driver.findElement(PriceMinInput.getLocator()).clear();
        sleep(500);
        setElementText(PriceMinInput.getElement(driver), "100", PriceMinInput.getDescription());
        waitForElementVisible(driver, PriceMaxInput.getLocator(), PriceMaxInput.getDescription(), 20);
        driver.findElement(PriceMaxInput.getLocator()).clear();
        sleep(500);
        setElementText(PriceMaxInput.getElement(driver), "1000", PriceMaxInput.getDescription());
        sleep(10000);
    }

    @And("Search {string}")
    public void search(String arg0) {

    }

    @And("Verify {string} is visible")
    public void verifyIsVisible(String arg0) {
    }


    @Then("Remember Item Count after price filter")
    public void rememberItemCountAfterPriceFilter() {
    }

    @Then("Click checkbox {string}")
    public void clickCheckbox(String arg0) {
    }

    @Then("Remember Item Count after brand filter")
    public void rememberItemCountAfterBrandFilter() {
    }

    @Then("Remember Item Count after all filters")
    public void rememberItemCountAfterAllFilters() {
    }

    @Then("Switch to tab {string}")
    public void switchToTab(String arg0) {
    }

    @Then("Verify first item price between {string} and {string}")
    public void verifyFirstItemPriceBetweenAnd(String arg0, String arg1) {
    }

    @Then("Verify product description contains {string}")
    public void verifyProductDescriptionContains(String arg0) {
    }

    @Then("Uncheck checkbox {string}")
    public void uncheckCheckbox(String arg0) {
    }

    @Then("Verify item count after uncheck item filter")
    public void verifyItemCountAfterUncheckItemFilter() {
    }

    @Then("Verify item count after uncheck brand filter")
    public void verifyItemCountAfterUncheckBrandFilter() {
    }

    @Then("Clear parameters")
    public void clearParameters() {
    }

    @Then("Verify item count after filter clearing")
    public void verifyItemCountAfterFilterClearing() {
    }
}
