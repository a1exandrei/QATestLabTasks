package myprojects.automation.assignment2;

import java.util.concurrent.TimeUnit;

import myprojects.automation.assignment2.utils.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class BaseScript {

    public static WebDriver getDriver(String driverName) {
        switch(driverName){
        	case"firefox":
        		return initFirefoxDriver();
        	case"chrome":
        		default:
        		return initChromeDriver();
        }
    }
    
    protected static WebDriver getConfiguredDriver(String drv){
		WebDriver driver = getDriver(drv); 
    	driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    	return driver;
    }
    
    protected static WebDriver initChromeDriver() {
		String chromePath = System.getProperty("user.dir")
				+"\\src\\main\\resources\\chromedriver.exe";
    	System.setProperty("webdriver.chrome.driver", chromePath);
		return new ChromeDriver();
	}

	protected static WebDriver initFirefoxDriver(){
	    String geckoPath = System.getProperty("user.dir")
	    		+"\\src\\main\\resources\\geckodriver.exe";
	    System.setProperty("webdriver.gecko.driver", geckoPath);
	    return new FirefoxDriver();
	}
	
	protected static void loginToAdminPanel(WebDriver driver){
        driver.get(Properties.BASE_ADMIN_URL);
        driver.findElement(By.id("email")).sendKeys(Properties.LOGIN);
        driver.findElement(By.id("passwd")).sendKeys(Properties.PASS);
        driver.findElement(By.name("submitLogin")).click();
	}
	
	protected static void logoutFromAdminPanel(WebDriver driver){
		driver.findElement(By.id("employee_infos")).click();
		driver.findElement(By.id("header_logout")).click();
	}
}
