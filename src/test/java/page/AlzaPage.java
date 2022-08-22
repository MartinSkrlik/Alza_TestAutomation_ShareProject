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
        SearchBar		    (By.xpath("//input[@aria-label='Search']"),
                "SEARCH BAR"),
        SearchButton        (By.xpath("//div[@id='btnSearch']"),
                "SEARCH BUTTON"),
        CookieAcceptButton  (By.xpath("//a[contains(text(), 'I understand')]"),
                "COOKIE ACCEPT"),
        ItemCountSpan  (By.xpath("//span[@class='numberItem']"),
                "ITEM COUNT SPAN"),
        PriceMinInput  (By.xpath("//input[contains(@class, 'min-value')]"),
                "PRICE MIN INPUT"),
        PriceMaxInput  (By.xpath("//input[contains(@class, 'max-value')]"),
                "PRICE MAX INPUT"),
        ;
        private String description;
        private By findBy;

        private alzaItems(By findBy, String description) {
            this.description = description;
            this.findBy = findBy;
        }

        public String getDescription(){
            return description;
        }

        public By getLocator(){
            return findBy;
        }

        public WebElement getElement(WebDriver driver) {
            return driver.findElement(getLocator());
        }
    }


}
