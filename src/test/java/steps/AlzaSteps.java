package steps;

import cucumber.api.java.en.And;
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
}
