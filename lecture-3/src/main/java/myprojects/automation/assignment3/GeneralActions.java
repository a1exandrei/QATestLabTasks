package myprojects.automation.assignment3;

import myprojects.automation.assignment3.utils.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GeneralActions {
    WebDriver driver;
    WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }

    public void login(WebDriver driver){
    	driver.findElement(By.id("email")).sendKeys(Properties.getLogin());
    	driver.findElement(By.id("passwd")).sendKeys(Properties.getPassword());
    	driver.findElement(By.name("submitLogin")).click();
    }
    
    public void createCategory(WebDriver driver, String categoryName){
    	WebElement catalogue = driver.findElement(By.id("subtab-AdminCatalog"));
    	WebElement category = driver.findElement(By.id("subtab-AdminCategories"));
    	
    	Actions action = new Actions(driver);
    	action.moveToElement(catalogue).build().perform();
    	
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	wait.until(ExpectedConditions.elementToBeClickable(category));
    	
    	action.moveToElement(category).click().build().perform();
    	
    	WebElement newCategory = driver.findElement(By.id("page-header-desc-category-new_category"));
    	wait.until(ExpectedConditions.elementToBeClickable(newCategory));
    	wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
    	
    	clickIgnoringStaleElementException(
    		driver.findElement(By.id("page-header-desc-category-new_category")));
    	
    	driver.findElement(By.id("name_1")).sendKeys(categoryName);
    	
    	WebElement submitButton = driver.findElement(By.id("category_form_submit_btn"));
    	scrollIntoView(submitButton, driver);
    	
    	clickIgnoringStaleElementException(submitButton);
    }
    
    public void scrollIntoView(WebElement element, WebDriver driver){
    	((JavascriptExecutor) driver).executeScript(
    			"arguments[0].scrollIntoView(true);", element);
    }
    
    public static void clickIgnoringStaleElementException(WebElement element){
    	try {
    		element.click();	
    	} catch (StaleElementReferenceException stEl){
    		// suppress StaleElementReferenceException
    	}
    }

    public void filterByCategory(String categoryName, WebDriver driver){
    	// close 'Created successfully' message
    	WebElement closeSuccesMessage = 
    			driver.findElement(By.cssSelector("div.alert.alert-success > button.close"));
    	clickIgnoringStaleElementException(closeSuccesMessage);
    	
    	// filter by category
    	driver.findElement(By.xpath("//input[@name='categoryFilter_name']")).sendKeys(categoryName);
    	
    	WebElement filterButton = driver.findElement(By.id("submitFilterButtoncategory"));
    	clickIgnoringStaleElementException(filterButton);
    }

}
