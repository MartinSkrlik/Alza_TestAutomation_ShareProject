package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AlzaPage {
    private WebDriver driver;

    public AlzaPage(WebDriver driver) {
        this.driver = driver;
    }

    public enum alzaItems {
        SearchBar(By.xpath("//input[@aria-label='Search']"),
                "SEARCH BAR"),
        SearchButton(By.xpath("//div[@id='btnSearch']"),
                "SEARCH BUTTON"),
        CookieAcceptButton(By.xpath("//a[contains(text(), 'I understand')]"),
                "COOKIE ACCEPT"),
        ItemCountSpan(By.xpath("//span[@class='numberItem']"),
                "ITEM COUNT SPAN"),
        PriceMinInput(By.xpath("//input[contains(@class, 'min-value')]"),
                "PRICE MIN INPUT"),
        PriceMaxInput(By.xpath("//input[contains(@class, 'max-value')]"),
                "PRICE MAX INPUT"),
        ItemCountAfterTwoFIlter(By.xpath("//span[@class='numberItem']"),
                "ITEM COUNT AFTER TWO FILTER"),
        ClearSelectedParameters(By.xpath("//div[@id='parametrization']//a[@id='clearFilter']"),
                "CLEAR SELECTED PARAMETERS"),
        TextForVerification(By.xpath("//*[@itemprop='name']"),
                "TITLE TABLETS FOR VERFICATION"),
        BrandsFilterGroup(By.xpath("//div[@id='producers']"),
                "//div[contains(text(),'Brands')]"),
        Description(By.xpath("//div[@class='Description']"),
                "Description of product"),
        SubpageName(By.xpath("(//li[contains(@class, 'ui-state-active')]/a)[1]"),
                "SUBPAGE NAME"),
        ;
        private String description;
        private By findBy;

        private alzaItems(By findBy, String description) {
            this.description = description;
            this.findBy = findBy;
        }

        public String getDescription() {
            return description;
        }

        public By getLocator() {
            return findBy;
        }

        public WebElement getElement(WebDriver driver) {
            return driver.findElement(getLocator());
        }

    }

    public WebElement getCheckBoxElement(String name) {
        return driver.findElement(getCheckBoxElementLocator(name));
    }

    public By getCheckBoxElementLocator(String name) {
        return By.xpath("//div[@id='parametrization']//*[normalize-space()= '" + name + "']//parent::div//label");
    }

    public WebElement getstatusElement(String name) {
        return driver.findElement(getstatusElementLocator(name));
    }

    public By getstatusElementLocator(String name) {
        return By.xpath("//div[@id='parametrization']//*[normalize-space()= '" + name + "']//parent::div//label[@class='alzacheckbox classic checked']");
    }

    public WebElement getUncheckElement(String name) {
        return driver.findElement(getUncheckElementLocator(name));
    }

    public By getUncheckElementLocator(String name) {
        return By.xpath("//div[@id='parametrization']//*[normalize-space()= '" + name + "']//parent::div//label[@class='alzacheckbox classic']");
    }

    public WebElement checkInputElement(String name) {
        return driver.findElement(checkInputElementLocator(name));
    }

    public By checkInputElementLocator(String name) {
        return By.xpath("//div[@id='parametrization']//*[normalize-space()= '" + name + "']//parent::div//input");
    }

    public WebElement getSubpageElement(String value) {
        return driver.findElement(getSubpageLocator(value));
    }

    public By getSubpageLocator(String value) {
        return By.xpath("//a[contains(text(), '" + value + "')]");
    }

    public WebElement getItemPriceByIndexElement(String value) {
        return driver.findElement(getItemPriceByIndexLocator(value));
    }

    public By getItemPriceByIndexLocator(String value) {
        return By.xpath("(//span[@class='price-box__price'])[" + value + "]");
    }
}
