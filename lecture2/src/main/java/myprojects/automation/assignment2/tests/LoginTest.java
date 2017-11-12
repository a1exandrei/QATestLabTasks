package myprojects.automation.assignment2.tests;

import myprojects.automation.assignment2.BaseScript;

import org.openqa.selenium.WebDriver;

public class LoginTest extends BaseScript {
	
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = getConfiguredDriver("firefox");
        loginToAdminPanel(driver);
        logoutFromAdminPanel(driver);
        driver.quit();
    }
}
