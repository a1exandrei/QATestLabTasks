package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.GeneralActions;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateProductTest extends BaseTest {

	WebDriver driver;
	GeneralActions generalActions;
	Actions act;
	JavascriptExecutor js;
	WebDriverWait wait;
	String addedName;
	String addedQty;
	String addedPrice;
	
	@DataProvider
	public Object[][] generateProduct() {
		ProductData data = ProductData.generate();
		return new Object [][] {
				{ data.getName(), data.getQty(), data.getPrice() }
		};
	}
	
	@Parameters("browser")
	@Test
    public void openProductsPage(String browser){ 
		
		driver = setUp(browser);
		generalActions = new GeneralActions(driver);
    	generalActions.login("webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw");
    	
    	generalActions.navigateToProductsPage();
    	generalActions.waitForContentLoad(driver);
    	
    	WebElement newProduct = driver.findElement(By
    			.cssSelector("#page-header-desc-configuration-add > span"));
    	newProduct.click();
    	    	
    	generalActions.waitForContentLoad(driver);
	}

    @Test (dependsOnMethods="openProductsPage", dataProvider="generateProduct")	
    public void fillNewProductData(String prodName, Integer quantity, String price) {
    
    	WebElement productName = driver.findElement(By.id("form_step1_name_1"));
    	productName.sendKeys(prodName);
    	
    	WebElement productQuantity = driver.findElement(By.id("form_step1_qty_0_shortcut"));
    	productQuantity.sendKeys(Keys.BACK_SPACE);
    	productQuantity.sendKeys(quantity.toString());
    	
    	js = (JavascriptExecutor)driver;
    	js.executeScript("scroll(0,400);");
    	
    	WebElement productPrice = driver.findElement(By.id("form_step1_price_shortcut"));
    	act = new Actions(driver);
    	act.doubleClick(productPrice).build().perform();
    	productPrice.sendKeys(Keys.BACK_SPACE);
    	productPrice.sendKeys(price);
    	
    	addedName = prodName; addedQty = quantity.toString(); addedPrice = price.replace(".", ",");
    }
	
	@Test(dependsOnMethods="fillNewProductData")
	public void saveProduct(){
		driver.findElement(By.cssSelector(".switch-input")).click();
		driver.findElement(By.xpath("//*[@id='growls']")).click();
		driver.findElement(By.cssSelector(".growl-close")).click();
		driver.findElement(By.cssSelector("button.btn.btn-primary.js-btn-save")).click();
	}
	
	@Test(dependsOnMethods="saveProduct")
	public void verifyCreatedProductInList(){
		driver.navigate().to(Properties.getBaseUrl());
		
		WebElement allProducts = driver.findElement(By
			.cssSelector("a.all-product-link.pull-xs-left.pull-md-right.h4")); 
		
		js.executeScript("arguments[0].scrollIntoView(true);", allProducts);
		allProducts.click();
		
		WebElement createdProductName = driver.findElement(By
			.xpath("//div[@class='product-price-and-shipping' and contains(.,'"+addedPrice+"')]/parent::div//a"));
		WebElement createdProductPrice = driver.findElement(By
			.xpath("//div[@class='product-price-and-shipping' and contains(.,'"+addedPrice+"')]/parent::div//span"));	
		
		Assert.assertEquals(createdProductName.getText(), addedName, 
				"Wrong product name");
		Assert.assertEquals(createdProductPrice.getText().substring(0,5).trim(), addedPrice, 
				"Wrong product price");
		
		js.executeScript("arguments[0].scrollIntoView(true);", createdProductName);
		createdProductName.click();
	}
	
	@Test(dependsOnMethods="verifyCreatedProductInList")
	public void verifyDetailedProductInformation(){
		
		WebElement productDetailsName = driver.findElement(By
			.cssSelector("h1.h1"));
		WebElement productDetailsPrice = driver.findElement(By
				.cssSelector("div.product-price.h5"));		
		WebElement productDetailsQty = driver.findElement(By
				.xpath("//div[@class='product-quantities']/span"));

		Assert.assertTrue(productDetailsName.getText().equalsIgnoreCase(addedName), 
				"Wrong name in product details");
		Assert.assertEquals(productDetailsPrice.getText().substring(0,5).trim(), addedPrice,
				"Wrong price in product details");
		
		if (productDetailsQty.getText() != "100"){
			Assert.assertEquals(productDetailsQty.getText().substring(0,2).trim(), addedQty,
				"Wrong quantity in product details");
		} else {
			Assert.assertEquals(productDetailsQty.getText().substring(0,3).trim(), addedQty, 
				"Wrong quantity in product details");
		}
	}
	
}
