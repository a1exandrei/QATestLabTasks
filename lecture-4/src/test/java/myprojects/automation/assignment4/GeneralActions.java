package myprojects.automation.assignment4;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import myprojects.automation.assignment4.utils.Properties;

/**
 * Contains main script actions that may be used in scripts.
 */
@SuppressWarnings("unused")
public class GeneralActions {
	private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String email, String password) {
    	
    	driver.navigate().to(Properties.getBaseAdminUrl());
    	
        By emailLocator = By.id("email");
        By passwordLocator = By.id("passwd");
        By loginBtnLocator = By.name("submitLogin");

        driver.findElement(emailLocator).sendKeys(email);
        driver.findElement(passwordLocator).sendKeys(password);
        driver.findElement(loginBtnLocator).click();
    }

    public void navigateToProductsPage() {
    	By catalogueLocator = By.id("subtab-AdminCatalog");
    	By goodsLocator = By.xpath("//*[@id='subtab-AdminProducts']/a");
    	
    	Actions act = new Actions(driver);
    	WebElement catalogueMenu = driver.findElement(catalogueLocator); 
    	act.moveToElement(catalogueMenu).build().perform();
    	WebElement goodsMenu = driver.findElement(goodsLocator);
    	act.moveToElement(goodsMenu).click().build().perform();
    }
    
    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad(WebDriver driver) {
    	
    	JavascriptExecutor js = (JavascriptExecutor)driver;
    	
    	  if (js.executeScript("return document.readyState").toString().equals("complete")){ 
    		   System.out.println("Page Is loaded.");
		  } 

    }

}
