package myprojects.automation.assignment5;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import myprojects.automation.assignment5.utils.logging.EventHandler;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;

/**
 * Base script functionality, can be used for all Selenium scripts.
 */
public abstract class BaseTest {

//	protected RemoteWebDriver driver;
	protected EventFiringWebDriver driver;
	protected GeneralActions actions;

	/**
	 *
	 * @param browser
	 *            Driver type to use in tests.
	 *
	 * @return New instance of {@link WebDriver} object.
	 */
	public WebDriver getDriver(String browser) {
		switch (browser) {
		case "android":
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Nexus 5");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			return new ChromeDriver(capabilities);
		case "firefox":
			System.setProperty("webdriver.gecko.driver",
					getResource("/geckodriver.exe"));
			return new FirefoxDriver();
		case "ie":
		case "internet explorer":
			System.setProperty("webdriver.ie.driver",
					getResource("/IEDriverServer.exe"));
			return new InternetExplorerDriver();
		case "chrome":
		default:
			System.setProperty("webdriver.chrome.driver",
					getResource("/chromedriver.exe"));
			return new ChromeDriver();
		}
	}
	
	private String getResource(String resourceName) {
		try {
			return Paths.get(BaseTest.class.getResource(resourceName).toURI())
					.toFile().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return resourceName;
	}

	public RemoteWebDriver getRemoteDriver(String browser) throws MalformedURLException {
		DesiredCapabilities caps = null;
		switch (browser) {
		default:
		case "grid_chrome":
			caps = DesiredCapabilities.chrome();
			break;
		case "grid_firefox":
			caps = DesiredCapabilities.firefox();
			break;
		case "grid_android":
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Nexus 5");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			caps = DesiredCapabilities.chrome();
			caps.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			break;
		}
		return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub/"),caps);
	}

	public EventFiringWebDriver setUp(String browser) throws MalformedURLException {
		if (browser.contains("grid")){
			return new EventFiringWebDriver(getRemoteDriver(browser));
		}
		
		driver = new EventFiringWebDriver(getDriver(browser));
		driver.register(new EventHandler());
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
