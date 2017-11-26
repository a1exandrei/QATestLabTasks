package myprojects.automation.assignment3;

import java.io.File;
import java.util.concurrent.TimeUnit;

import myprojects.automation.assignment3.utils.EventHandler;
import myprojects.automation.assignment3.utils.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;


public abstract class BaseScript {

    public static WebDriver getDriver() {
        String browser = Properties.getBrowser();
        switch (browser) {
        	case "ie":
        		System.setProperty("webdriver.ie.driver",
        				new File("user.dir" + "\\resources\\IEDriverServer.exe").getPath());
        		return new InternetExplorerDriver();
        	case "firefox":
        		System.setProperty("webdriver.firefox.driver",
        				new File("user.dir" + "\\resources\\geckodriver.exe").getPath());
        		return new FirefoxDriver();
        	case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(BaseScript.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }

    public static EventFiringWebDriver getConfiguredDriver() {
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        EventFiringWebDriver firingDriver = new EventFiringWebDriver(driver);
        firingDriver.register(new EventHandler());
        return firingDriver;
    }
    
}
