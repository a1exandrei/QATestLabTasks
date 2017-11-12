package myprojects.automation.assignment2.tests;

import java.util.ArrayList;
import java.util.List;

import myprojects.automation.assignment2.BaseScript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckMainMenuTest extends BaseScript {
	
	static String initialTitle;
	static String refreshedTitle;
	
	static By dashboard 	 = By.id("tab-AdminDashboard");
	static By orders		 = By.id("subtab-AdminParentOrders");
	static By catalogue	  	 = By.id("subtab-AdminCatalog");
	static By clients		 = By.id("subtab-AdminParentCustomer");
	static By support 		 = By.id("subtab-AdminParentCustomerThreads");
	static By statistics 	 = By.id("subtab-AdminStats");
	static By modules 		 = By.id("subtab-AdminParentModulesSf");
	static By design 		 = By.id("subtab-AdminParentThemes");
	static By delivery 	  	 = By.id("subtab-AdminParentShipping");
	static By paymentMethod  = By.id("subtab-AdminParentPayment");
	static By international  = By.id("subtab-AdminInternational");
	static By shopParameters = By.id("subtab-ShopParameters");
	static By configuration  = By.id("subtab-AdminAdvancedParameters");
	
	public static void main(String[] args) throws Exception {
		
		WebDriver driver = getConfiguredDriver("firefox");
		loginToAdminPanel(driver);
		
		List<By> locators = new ArrayList<By>();
		locators.add(dashboard);
		locators.add(orders);
		locators.add(catalogue);
		locators.add(clients);
		locators.add(support);
		locators.add(statistics);
		locators.add(modules);
		locators.add(design);
		locators.add(delivery);
		locators.add(paymentMethod);
		locators.add(international);
		locators.add(shopParameters);
		locators.add(configuration);
		
		
		for (int i=0; i<locators.size(); i++){
			if (i != 3 && i != 7){
				verifyRefreshedTitle(driver, locators.get(i));
			} else {
				List<WebElement> byClass = driver
						.findElements(By.cssSelector("li.link-levelone"));
				verifyRefreshedTitle(driver, byClass.get(i));	
			}
		}
		
	}
	
	public static void verifyRefreshedTitle(WebDriver driver, By id) throws Exception{
		clickMenuItem(driver, id);
		getInitialTitle(driver);
		refreshPage(driver);
		compareTitle(driver);
	}
	
	public static void verifyRefreshedTitle(WebDriver driver, WebElement element) throws Exception{
		clickMenuItem(driver, element);
		getInitialTitle(driver);
		refreshPage(driver);
		compareTitle(driver);
	}
	
	public static void clickMenuItem(WebDriver driver, By locator){
		driver.findElement(locator).click();
	}
	
	public static void clickMenuItem(WebDriver driver, WebElement element){
		element.click();
	}
	
	public static void getInitialTitle(WebDriver driver) {
		initialTitle = driver.getTitle();
		System.out.println("Initial title: " + initialTitle);
	}
	
	public static void refreshPage(WebDriver driver){
		driver.navigate().refresh();
	}
	
	public static void compareTitle(WebDriver driver) throws Exception{
		refreshedTitle = driver.getTitle();
		System.out.println("Refreshed title: " + refreshedTitle);
		if (!initialTitle.equals(refreshedTitle)) {
			throw new Exception(
					"Page titles after refresh are different ");
		}
	}
	
}
