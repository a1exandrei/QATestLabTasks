package myprojects.automation.assignment3.tests;

import myprojects.automation.assignment3.BaseScript;
import myprojects.automation.assignment3.GeneralActions;
import myprojects.automation.assignment3.utils.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@SuppressWarnings("unused")
public class CreateCategoryTest extends BaseScript {
	
	public static void main(String[] args){
		
		String categoryName = "Custom Category";
		
    	WebDriver driver = getConfiguredDriver();
		GeneralActions utils = new GeneralActions(driver);
    	
    	driver.get(Properties.getBaseAdminUrl());
    	utils.login(driver);
    	utils.createCategory(driver, categoryName);
    	
    	WebElement createdSuccessfully = driver.findElement(By.xpath(
    			"//div[@class='bootstrap' and contains(.,'Создано')]" ));  	
    	
    	utils.filterByCategory(categoryName, driver);

    	WebElement categoryInList = driver.findElement(By.xpath(
    			"//td[@class='pointer'and text()[contains(.,'" + categoryName + "')]]" ));
    	
    	driver.quit();
    }
}
