package myprojects.automation.assignment5.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Random;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.utils.Properties;
import myprojects.automation.assignment5.utils.UserData;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {
	
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;
	
	String name;
	String realName;
	String price;
	String quantity;
	Integer qtyInt;
	Integer changedQtyInt;
	
	By cart = By.xpath("//div[@class='header']/span[1]");
	By mobileLogo = By.id("_mobile_logo");
	By mobileMenuIcon = By.cssSelector("i.material-icons.d-inline");
	By mobileUserIcon = By.id("_mobile_user_info");
	By mobileShoppingCart = By.cssSelector("i.material-icons.shopping-cart");
	By dropDownItem = By.cssSelector("#category-3 > a");
	By contactLink = By.cssSelector("#contact-link > a");
	By allProducts = By.xpath("//a[@class='all-product-link' or contains(.,'Все товары')]");
	By productsList = By.cssSelector(".product-description");
	By pname = By.cssSelector("h1.h1");
	By pprice = By.cssSelector("div.current-price > span");
	By qty = By.cssSelector("div.product-quantities > span");
	By addToCart = By.cssSelector("button.btn.btn-primary.add-to-cart");
	By checkOut = By.cssSelector("a.btn.btn-primary");
	By cartName = By.cssSelector("a.btn.btn-primary");
	By cartPrice = By.cssSelector("div.product-line-info > span.value:nth-child(1)");
	By cartQty = By.cssSelector("input.js-cart-line-product-quantity.form-control");
	By cartCheckOut = By.cssSelector("a.btn.btn-primary");
	By checkOutName = By.name("firstname");
	By checkOutLastName = By.name("lastname");
	By checkOutEmail = By.name("email");
	By btnContinue = By.cssSelector("button.continue"); 
	By checkOutAddress = By.name("address1");
	By postcode = By.name("postcode");
	By city = By.name("city");
	By confirmAddress = By.name("confirm-addresses");
	By confirmDelivery = By.name("confirmDeliveryOption");
	By paymentOptionBank = By.xpath("//*[@id='payment-option-2-container']/label/span");
	By conditionsApprove = By.name("conditions_to_approve[terms-and-conditions]");
	By order = By.cssSelector("button.btn.btn-primary.center-block");
	By orderConfirmHeader = By.cssSelector("h3.h1.card-title");
	By orderConfirmName = By.cssSelector("div.col-sm-4.col-xs-9.details");
	By orderConfirmPrice = By.cssSelector("div.col-xs-5.text-sm-right.text-xs-left");
	By orderConfirmQty = By.cssSelector("div.col-xs-2");
	By all = By.cssSelector("a.all-product-link");
	
    @Test
    @Parameters("browser")
    public void checkSiteVersion(String browser) throws MalformedURLException {
    	driver = setUp(browser);
    	driver.get(Properties.getBaseUrl());
        // open main page and validate website version
    	if (browser.equals("android")){
    		androidCheckSiteVersion();
    	} else {
    		assertEquals(driver.findElement(contactLink).getText(), "Свяжитесь с нами", "No Contact link");
    		assertEquals(driver.findElement(dropDownItem).getText(), "WOMEN", "No Dropdown item");
    		assertEquals(driver.findElement(cart).getText(), "Корзина", "No Shopping cart");
    	}
    }

    @Test(dependsOnMethods="checkSiteVersion")
    public void createNewOrder() {
    	
    	clickRandomProduct();
    	createOrder();
		submitAndVerifyOrder();
    }
    
    public void androidCheckSiteVersion(){
    	assertNotNull(driver.findElement(mobileMenuIcon),"Menu Icon not found");
    	assertNotNull(driver.findElement(mobileLogo),"Classic Logo not found");
    	assertNotNull(driver.findElement(mobileUserIcon),"User Icon not found");
    }
    
    public void clickRandomProduct(){	
        // click "All products"
		WebElement allProds = driver.findElement(allProducts); 
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", allProds);
		clickChangingLocator(allProducts);
			
        // open random product
		List<WebElement> products = driver.findElements(productsList);
		WebElement randomProduct = products.get((int)new Random().nextInt(5)+7); 
		
		String [] split = randomProduct.getText().split("\n");
		realName = split[0];
		
		clickChangingLocator(randomProduct);
    }
    
    public void createOrder(){
        // save product parameters
		name = driver.findElement(pname).getText().trim();
		price = driver.findElement(pprice).getText().substring(0, 5).trim();
		quantity = driver.findElement(qty).getText().substring(0, 2).trim();
		qtyInt = Integer.valueOf(quantity);

        // add product to Cart and validate product information in the Cart
		driver.findElement(addToCart).click();
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(checkOut)));
		clickChangingLocator(checkOut);
		assertEquals(driver.findElement(cartPrice).getText().substring(0, 5).trim(),
				price, "Wrong price in cart");
		assertEquals(driver.findElement(cartQty).getAttribute("value"), 
				"1", "Wrong quantity in cart");
		
        // proceed to order creation, fill required information
		clickChangingLocator(cartCheckOut);
		UserData gen = new UserData();
		driver.findElement(checkOutName).sendKeys(gen.generateName());
		driver.findElement(checkOutLastName).sendKeys(gen.generateLastName());
		driver.findElement(checkOutEmail).sendKeys(gen.generateEmail());
		clickChangingLocator(btnContinue);
		
		driver.findElement(checkOutAddress).sendKeys(gen.generateAddress());
		driver.findElement(postcode).sendKeys(gen.generateZip());
		driver.findElement(city).sendKeys(gen.generateCity());
		clickChangingLocator(confirmAddress);
    }

	public void submitAndVerifyOrder(){
        // place new order and validate order summary
		clickChangingLocator(confirmDelivery);
		driver.findElement(paymentOptionBank).click();
		driver.findElement(conditionsApprove).click();
		clickChangingLocator(order);

		assertEquals(driver.findElement(orderConfirmHeader).getText().length(),
				22, "Wrong order confirmation header text size"); 
		assertEquals(driver.findElement(orderConfirmPrice).getText()
				.substring(0,5).trim(), price, "Wrong order confrimation price");
		assertEquals(driver.findElement(orderConfirmQty).getText(),
				"1","Wrong order confirmation quantity");
		
        // check updated In Stock value
		WebElement allprod = driver.findElement(all);
		js.executeScript("arguments[0].scrollIntoView(true);", allprod);
		clickChangingLocator(allprod);
		
		WebElement prod = driver.findElement(By.xpath("//h1//a[contains(.,'"+realName+"')]"));
		clickChangingLocator(prod);
		
		String changedQty = driver.findElement(By
				.cssSelector("div.product-quantities > span"))
				.getText().substring(0, 2).trim();
		
		changedQtyInt = Integer.valueOf(changedQty);
		
		assertEquals((int)qtyInt-1 , (int)changedQtyInt, 
				"Wrong product quantity after checkout");
	}
    
    public void clickChangingLocator(By by) {
        try {
            driver.findElement(by).click();
        } catch(StaleElementReferenceException e) {
            // Suppressed StaleElementReferenceException
        }
    }
    
    public void clickChangingLocator(WebElement webelement) {
        try {
            webelement.click();
        } catch(StaleElementReferenceException e) {
        	// Suppressed StaleElementReferenceException
        }
    }

}
