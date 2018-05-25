package tests;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import pages.DashboardPageObject;
import pages.LoginPageObject;

public class LoginTest {
	
	WebDriver driver;
	String webUrl = "http://mosaic-test-app.s3-website.eu-west-2.amazonaws.com/";
	String errorMessage = "";
	
	LoginPageObject loginPage;
	DashboardPageObject dashboardPage;
	
	/* Test to check if text fields and button for login is displayed */
	@Test
	public void loginElementsPresent()
	{
		boolean usernameFieldPresent = loginPage.usernameFieldPresent();
		boolean passwordFieldPresent = loginPage.passwordFieldPresent();
		boolean submitButtonPresent = loginPage.SubmitButtonPresent();
		
		Assert.assertTrue(usernameFieldPresent, "Username Text Field is missing");
		Assert.assertTrue(passwordFieldPresent, "Password Text Field is missing");
		Assert.assertTrue(submitButtonPresent, "Submit button is missing");
	}
	
	/* Test to check if login is successful with valid credentials */
	@Test
	public void loginWithValidCredentials()
	{
		String userName = "test1";
		String password = "passW0rd";
		
		loginPage.setUserName(userName);
		loginPage.setPassword(password);
		loginPage.clickSubmit();
		// Check Dashboard Page to confirm successful login
		dashboardPage = new DashboardPageObject(driver);
		String expectedText = "Dashboard";
		errorMessage = "Login is not working correctly with valid credentials";
		// Assertion 
		Assert.assertEquals(dashboardPage.getDashboardText(), expectedText, errorMessage);
	}
	
	/* Test to check login fails if no credentials are entered
	 * This test will fail currently as we are able to login with no credentials */
	@Test
	public void loginWithNoCredentials()
	{
		String username = "";
		String password = "";
		errorMessage = "Login shouldn't work with no or invalid credentials";
		
		loginPage.setUserName(username);
		loginPage.setPassword(password);
		loginPage.clickSubmit();
		dashboardPage = new DashboardPageObject(driver);
		Assert.assertFalse(dashboardPage.dashboardTextVisible(), errorMessage);
	}
	
	/* Test to validate logout of website. 
	 * This test will fail currently as the click on signout button is not working. */
	@Test
	public void logoutTest()
	{
		dashboardPage = new DashboardPageObject(driver);
		dashboardPage.clickSignout();
		errorMessage = "Click on Signout button is not working";
		Assert.assertFalse(dashboardPage.dashboardTextVisible(), errorMessage);
		
	}
	
	// Runs before every test
	@BeforeMethod
	public void setUp()
	{
		driver = utilities.WebDriverFactory.Open("chrome");
		driver.get(webUrl);
		loginPage = new LoginPageObject(driver);
	}
	
	// Runs after every test
	@AfterMethod
	public void tearDown()
	{
		driver.close();
		driver.quit();
	}

}
