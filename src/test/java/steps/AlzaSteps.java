package steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
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
    String itemsAfterPriceFiltering = "";
    String itemsAfterFilteringBrand = "";
    String itemsAfterFilteringItems = "";
    String hoverText = "";
    String productTitle = "";

    @And("Remember Item Count before filtering")
    public void rememberItemCountBeforeFiltering() {
        sleep(2000);
        itemsPreFiltering = getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription());
        ReportExtender.logInfo(itemsPreFiltering);
    }

    @And("Set price range between {string} to {string}")
    public void setPriceRangeBetweenTo(String arg0, String arg1) {
        waitForElementVisible(driver, PriceMinInput.getLocator(), PriceMinInput.getDescription(), 20);
        clearAndSet(PriceMinInput.getElement(driver), arg0);
        sleep(2000);
        waitForElementVisible(driver, PriceMaxInput.getLocator(), PriceMaxInput.getDescription(), 20);
        clearAndSet(PriceMaxInput.getElement(driver), arg1);
    }

    @And("Accept Cookies")
    public void acceptCookies() {
        waitIfElementAppears(driver, CookieAcceptButton.getLocator(), CookieAcceptButton.getDescription(), 20);
        clickElement(CookieAcceptButton.getElement(driver), CookieAcceptButton.getDescription());
    }

    @And("Search {string}")
    public void search(String arg0) {
        waitForElementVisible(driver, SearchBar.getLocator(), SearchBar.getDescription(), 20);
        setElementText(SearchBar.getElement(driver), arg0, SearchBar.getDescription());
        clickElement(SearchButton.getElement(driver), SearchButton.getDescription());
    }

    @And("Verify Page Title {string} is visible")
    public void verifyPageTitle(String title) {
        waitForElementVisible(driver, page.getPageTitleLocator(title), PageTitle.getDescription(), 60);
        new Validation("Page Title", getElementText(page.getPageTitleElement(title), PageTitle.getDescription()), title).contains();
        sleep(2000);
        ReportExtender.logScreen(driver);
    }

    @Then("Remember Item Count after price filter")
    public void rememberItemCountAfterPriceFilter() {
        sleep(2000);
        itemsAfterPriceFiltering = getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription());
        ReportExtender.logInfo(itemsAfterPriceFiltering);
    }

    @Then("Click checkbox {string}")
    public void click_Checkbox(String value) {
        waitForElementVisible(driver,page.getCheckBoxElementLocator(value),BrandsFilterGroup.getDescription(),10 );
        scrollElementIntoMiddleOfScreen(driver,page.getCheckBoxElement(value));
        checkCheckbox(page.getCheckBoxElement(value), "CLICK ON CHECKBOX");
        verifyAlzaIsChecked(driver, page.getCheckBoxElementLocator(value));
    }

    @Then("Remember Item Count after brand filter")
    public void rememberItemCountAfterBrandFilter() {
        sleep(2000);
        itemsAfterFilteringBrand = getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription());
        ReportExtender.logInfo(itemsAfterFilteringBrand);
    }

    @Then("Remember Item Count after all filters")
    public void rememberItemCountAfterAllFilters() {
        sleep(2000);
        itemsAfterFilteringItems = getElementText(ItemCountAfterTwoFIlter.getElement(driver), ItemCountAfterTwoFIlter.getDescription());
        ReportExtender.logInfo(itemsAfterFilteringItems);
    }

    @Then("Switch to subpage {string}")
    public void switchToSubpage(String arg0) {
        waitForElementVisible(driver, page.getSubpageLocator(arg0), "SUBPAGE", 20);
        clickElement(page.getSubpageElement(arg0), "SUBPAGE");
    }

    @Then("Verify first item price between {string} and {string}")
    public void verifyFirstItemPriceBetweenAnd(String arg0, String arg1) {
        scrollElementIntoMiddleOfScreen(driver,page.getItemPriceByIndexElement("1"));
        String stringItemPrice = getElementText(page.getItemPriceByIndexElement("1"), "FIRST ITEM PRICE");
        stringItemPrice = stringItemPrice.replace("&nbsp;€", "");
        double itemPrice = Double.parseDouble(stringItemPrice);
        double min = Double.parseDouble(arg0);
        double max = Double.parseDouble(arg1);

        boolean isInRange = false;
        if (itemPrice >= min && itemPrice <= max){
            isInRange = true;
        }
        new Validation("Verify item price is price filter range", isInRange).isTrue();
    }

    @Then("Verify product description contains {string}")
    public void verifyProductDescriptionContains(String checkbox) {
        scrollElementIntoMiddleOfScreen(driver, BrandsFilterGroup.getElement(driver));
        List<WebElement> elements = driver.findElements(Description.getLocator());
        for (WebElement element : elements) {
            new Validation("DESCRIPTION", element.getText(), checkbox).contains();
        }
        sleep(1000);
    }

    @Then("Uncheck checkbox {string}")
    public void uncheckCheckbox(String value) {
        scrollElementIntoMiddleOfScreen(driver,page.getCheckBoxElement(value));
        checkCheckbox(page.getCheckBoxElement(value), "UNCHECK CHECKBOX");
        verifyAlzaIsUnchecked(driver, page.getCheckBoxElementLocator(value));

    }

    @Then("Verify item count after uncheck item filter")
    public void verifyItemCountAfterUncheckItemFilter() {
        sleep(2000);
        new Validation("VALIDATION AFTER ITEM FILTER", getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription()), itemsAfterFilteringBrand).stringEquals();
        scrollElementIntoMiddleOfScreen(driver, ItemCountSpan.getElement(driver));

    }

    @Then("Verify item count after uncheck brand filter")
    public void verifyItemCountAfterUncheckBrandFilter() {
        sleep(2000);
        new Validation("VALIDATION AFTER BRAND FILTER", getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription()), itemsAfterPriceFiltering).stringEquals();
        scrollElementIntoMiddleOfScreen(driver, ItemCountSpan.getElement(driver));
    }

    @Then("Clear parameters")
    public void clearParameters() {
        clickElement(ClearSelectedParameters.getElement(driver), ClearSelectedParameters.getDescription());
    }

    @Then("Verify item count after filter clearing")
    public void verifyItemCountAfterFilterClearing() {
        sleep(2000);
        new Validation("VALIDATION AFTER CLEARING ALL FILTERS", getElementText(ItemCountSpan.getElement(driver), ItemCountSpan.getDescription()), itemsPreFiltering).stringEquals();
        scrollElementIntoMiddleOfScreen(driver, ItemCountSpan.getElement(driver));
    }

    @Then("Verify subpage {string} is active")
    public void verifySubpageIsActive(String arg0) {
        sleep(2000);
        new Validation("Verify active subpage", getElementText(SubpageName.getElement(driver), SubpageName.getDescription()), arg0).stringEquals();
    }

    @And("Do a screenshot")
    public void doAScreenshot() {
        ReportExtender.logScreen(driver);
    }

    boolean failedToLocate = false;
    @Then("Click on Product with Variants {string}")
    public void clickOnProductWithVariants(String n_product) {
        sleep(2000);
        while(!verifyElementIsPresent(driver,page.getProductVariantsLocator(n_product),ProductWithVariants.getDescription())) {
            if(!verifyElementIsPresent(driver,LoadMoreBtn.getLocator(),LoadMoreBtn.getDescription())){
                failedToLocate = true;
                break;
            }else{
                ReportExtender.logWarning("Product has no variants.");
            }
            clickElement(LoadMoreBtn.getElement(driver), LoadMoreBtn.getDescription());
            sleep(2000);
            ReportExtender.logScreen(driver);
        }
        if(!failedToLocate) {
            productTitle = getElementText(page.getProductVariantsElement(n_product), ProductWithVariants.getDescription());
            scrollElementIntoMiddleOfScreen(driver, page.getProductVariantsElement(n_product));
            waitForFullPageLoad(driver, 10);
            ReportExtender.logScreen(driver);
            clickElement(page.getProductVariantsElement(n_product), ProductWithVariants.getDescription());
            waitForFullPageLoad(driver, 10);
        }
    }

    @And("Verify Title Product Page")
    public void verifyTitleProductPage() {
        if(!failedToLocate) {
            new Validation("PRODUCT PAGE TITLE", getElementText(ProductTitle.getElement(driver), ProductTitle.getDescription()), productTitle).contains();
            ReportExtender.logScreen(driver);
        }
    }

    @Then("Verify Product Variants")
    public void verifyProductVariants() {
        int elementsCount = driver.findElements(Tiles.getLocator()).size();
        for (int i = 1; i<= elementsCount; i++ ) {
            clickElement(page.getTileElement(i),Tiles.getDescription());
            waitForFullPageLoad(driver,10);
            mouseOverElement(driver, page.getTileElement(i), Tiles.getDescription());
            waitForElementVisible(driver, HoverText.getLocator(),HoverText.getDescription(),10);
            hoverText = getElementText(HoverText.getElement(driver),HoverText.getDescription());
            ReportExtender.logScreen(driver);
            ReportExtender.logInfo(hoverText);
            new Validation("PRODUCT TITLE",getElementText(ProductTitle.getElement(driver),ProductTitle.getDescription()),hoverText).contains();
            ReportExtender.logScreen(driver);
        }
    }

}
