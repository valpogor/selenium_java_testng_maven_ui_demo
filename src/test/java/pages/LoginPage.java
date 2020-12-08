package pages;

import common.*;
import org.openqa.selenium.*;
import org.testng.*;
import java.util.*;
import java.util.ResourceBundle;
import static common.Utility.*;
import static java.util.ResourceBundle.getBundle;

public class LoginPage extends TestBase {
    private final ResourceBundle resourceBundle;
    public static String url_prod;
    public static String email;
    public static String password;
    public static String buybutton;
    public static String rentbutton;
    public static String sellbutton;
    public static String compassexcbutton;
    public static String newdevelopmentbutton;
    public static String agentsbutton;
    public static String loginbutton;
    public static String buyselbutton;
    public static String emailbutton;
    public static String signinbutton;
    public static String searchfield;
    public static String searchbutton;
    public static String takehomebutton;
    public static String selectsecondelementdrowpown;
    public static String locationbutton;
    public static String searchbarlocations;
    public static String foundneighborhoodtabs;
    public static String pricestab;
    public static String clearbutton;
    public static String checkbox;
    public static String saveas;
    public static String selectoptionbutton;
    public static String searchbar;
    public static String pricebutton;
    public static String cancelicon;

    public LoginPage() {
        resourceBundle = getBundle("pages.LoginPage");
        url_prod = this.getResourceBundle().getString("url_prod");
        email = this.getResourceBundle().getString("email");
        password = this.getResourceBundle().getString("password");
        buybutton = this.getResourceBundle().getString("buybutton");
        rentbutton = this.getResourceBundle().getString("rentbutton");
        sellbutton = this.getResourceBundle().getString("sellbutton");
        compassexcbutton = this.getResourceBundle().getString("compassexcbutton");
        newdevelopmentbutton = this.getResourceBundle().getString("newdevelopmentbutton");
        agentsbutton = this.getResourceBundle().getString("agentsbutton");
        loginbutton = this.getResourceBundle().getString("loginbutton");
        buyselbutton = this.getResourceBundle().getString("buyselbutton");
        emailbutton = this.getResourceBundle().getString("emailbutton");
        signinbutton = this.getResourceBundle().getString("signinbutton");
        searchfield = this.getResourceBundle().getString("searchfield");
        searchbutton = this.getResourceBundle().getString("searchbutton");
        takehomebutton = this.getResourceBundle().getString("takehomebutton");
        selectsecondelementdrowpown = this.getResourceBundle().getString("selectsecondelementdrowpown");
        locationbutton = this.getResourceBundle().getString("locationbutton");
        searchbarlocations = this.getResourceBundle().getString("searchbarlocations");
        foundneighborhoodtabs = this.getResourceBundle().getString("foundneighborhoodtabs");
        pricestab = this.getResourceBundle().getString("pricestab");
        clearbutton = this.getResourceBundle().getString("clearbutton");
        checkbox = this.getResourceBundle().getString("checkbox");
        saveas = this.getResourceBundle().getString("saveas");
        selectoptionbutton = this.getResourceBundle().getString("selectoptionbutton");
        searchbar = this.getResourceBundle().getString("searchbar");
        pricebutton = this.getResourceBundle().getString("pricebutton");
        cancelicon = this.getResourceBundle().getString("cancelicon");
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void verifyLoginPageHeadersButtons() {
        waitUntilElementDisplayed(driver, 10, By.xpath(buybutton));
        Assert.assertTrue(driver.findElement(By.xpath(buybutton)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(rentbutton)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(sellbutton)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(compassexcbutton)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(newdevelopmentbutton)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(agentsbutton)).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath(loginbutton)).isDisplayed());
    }

    public static void login(String email, String password) {
        if(driver.findElement(By.xpath(loginbutton)).isDisplayed()) {
            driver.findElement(By.xpath(loginbutton)).click();
            driver.findElement(By.xpath(buyselbutton)).click();
            driver.findElement(By.xpath(emailbutton)).click();
            driver.findElement(By.name("email")).sendKeys(email);
            driver.findElement(By.id("continue")).click();
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.id(signinbutton)).click();
            waitUntilElementDisplayed(driver, 10, By.xpath(takehomebutton));
            Assert.assertTrue(driver.findElement(By.xpath(takehomebutton)).isDisplayed());
        }
    }

    public static void searchBy(String text) {
        driver.findElement(By.xpath(searchfield)).sendKeys(text);
        clickVisibleElement(selectsecondelementdrowpown,10);
    }

    public static void switchNeighborhood(int order){
        if(order!=1){
            driver.findElement(By.xpath(locationbutton)).click();
            if(driver.findElement(By.xpath(clearbutton)).isDisplayed()) {
                driver.findElement(By.xpath(clearbutton)).click();
            }
            driver.findElement(By.xpath("("+foundneighborhoodtabs+")["+order+"]")).click();
            WebElement element = driver.findElement(By.xpath(searchbarlocations));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        }
        if(order>=3) {
            waitClickEl("(" + checkbox + ")[3]", 10);
        }
        else{
            waitClickEl("(" + checkbox + ")["+(order+1)+"]", 10);
        }
        driver.findElement(By.xpath(saveas)).click();
    }

    public static void adjustPrice(){
        waitClickEl(selectoptionbutton, 10);
        waitClickEl(pricebutton, 10);
        waitClickEl(pricebutton, 10);
    }

    public static void removeFilter() {
        try{
            waitClickEl(searchbar, 10);
            waitClickEl(cancelicon, 10);}
        catch (ElementNotVisibleException e){};
    }

    public static void verifySearchMostExpensiveSaleListing(int numbers) throws Exception{
        login(email,password);
        searchBy("New York");
        removeFilter();
        Utility.waitClickElement(locationbutton, searchbarlocations , 10);
        List<WebElement> neighborhood = new LinkedList<>();
        List<WebElement> foundneighborhood = driver.findElements(By.xpath(foundneighborhoodtabs));
        for ( WebElement nb : foundneighborhood ) {
            if(!nb.getText().isEmpty()&&!nb.getText().contains("Saved Boundaries")){
                neighborhood.add(nb);
            }
        }
        List<String> urls = new LinkedList<>();
        for (int i = 1; i <= neighborhood.size(); i++) {
            switchNeighborhood(i);
            adjustPrice();
            List<WebElement> prices = driver.findElements(By.xpath(pricestab));
            for (int n = 1; n <= numbers; n++) {
                urls.add(prices.get(n).getAttribute("href"));
                System.out.println("URL for most expensive for sale listings in NYC is: " + prices.get(n).getAttribute("href"));
            }
        }
        Utility.writeOutputToXLS(urls);
        Utility.writeOutputHtml(urls);
    }
}
