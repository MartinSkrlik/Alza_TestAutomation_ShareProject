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
        ClearSelectedParameters(By.xpath("//a[@id='clearFilter']"),
                "CLEAR SELECTED PARAMETERS"),
        BrandsFilterGroup(By.xpath("//div[@id='producers']"),
                "Brands Filter Group"),
        Description(By.xpath("//div[@class='Description']"),
                "Description of product"),
        SubpageName(By.xpath("(//li[contains(@class, 'ui-state-active')]/a)[1]"),
                "SUBPAGE NAME"),
        PageTitle (null, "Page title"),

        ProductWithVariants (null, "Product with variants"),

        Tiles (By.xpath("(//a[@class='group-link'])"), "Tiles"),

        HoverText (By.xpath("//div[@class='group selected']/span"), "Hover Text"),

        ProductTitle (By.xpath("//div[@id='h1c']/h1"),"Product Title"),

        LoadMoreBtn (By.xpath("//a[@class='js-button-more button-more btnx normal']"), "Load More Button"),
        VarrantyClaimPageTitle (By.xpath("//div[@id='h1c']/h1"), "VARRANTY CLAIM PAGE TITLE"),
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
    public WebElement getPageTitleElement (String title) {return driver.findElement(getPageTitleLocator(title));}
    public By getPageTitleLocator (String title) {return By.xpath("//h1[contains(text(),'" + title + "')]");}

    public WebElement getProductVariantsElement (String n_product) {return driver.findElement(getProductVariantsLocator(n_product));}
    public By getProductVariantsLocator (String n_product) {return By.xpath("(//div[span[contains(text(),'Variants')]]/following-sibling::div/a)[" + n_product + "]");}

    public WebElement getTileElement (int index) {return driver.findElement(getTileLocator(index));}
    public By getTileLocator (int index) {return By.xpath("(//a[@class='group-link'])[" + index + "]");}

    public WebElement getButtonElement (String text) {return driver.findElement(getButtonLocator(text));}
    public By getButtonLocator(String text) {return By.xpath("//a[text()='"+text+"']");}

    public WebElement getTitleElement (String text) {return driver.findElement(getTitleLocator(text));}
    public By getTitleLocator (String text) {return By.xpath("//div[contains(text(),'"+text+"')]");}

    public WebElement getTabElement (String text) {return driver.findElement(getTabLocator(text));}
    public By getTabLocator (String text) {return By.xpath("//span[contains(text(),'"+text+"')]");}

    public WebElement getSelectedButtonElement (String text) {return driver.findElement(getTabLocator(text));}
    public By getSelectedButtonLocator (String text) {return By.xpath("//a[contains(@class,'selected')]/span[contains(text(),'"+text+"')]");}

    public WebElement getSelectedButtonSecondElement (String text) {return driver.findElement(getSelectedButtonSecondLocator(text));}
    public By getSelectedButtonSecondLocator (String text) {return By.xpath("//a[normalize-space()= '"+text+"' and contains(@class,'blue')]");}

    public WebElement getListElement (String text) {return driver.findElement(getListLocator(text));}
    public By getListLocator (String text) {return By.xpath("//h3[contains(text(),'"+text+"')]");}

    public WebElement getServiceElement (String text) {return driver.findElement(getServiceLocator(text));}
    public By getServiceLocator (String text) {return By.xpath("//a[contains(text(),'"+text+"')]");}

    public WebElement getNumberElement (String text) {return driver.findElement(getNumberLocator(text));}
    public By getNumberLocator (String text) {return By.xpath("//*[normalize-space()= '"+text+"']");}


}
