package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.APIResponsePageObject;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class APIResponseTest {
	
	WebDriver driver;
	String webUrl = "http://api.fixer.io/latest?base=GBP";
	String errorMessage = "";
	APIResponsePageObject responsePage;
	
	/* Test to check the presence of rate jsonobject in the api */
	@Test
	public void checkRateDataPresentInAPI() throws ParseException
	{
		int rateAPICount = responsePage.getRateDataFromAPI().size();
		errorMessage = "Rate object not present in api";
		assertTrue(rateAPICount > 0, errorMessage);
	}
	
	// Runs before every test
	@BeforeMethod
	public void setUp()
	{
		driver = utilities.WebDriverFactory.Open("chrome");
		driver.get(webUrl);
		responsePage = new APIResponsePageObject(driver);
	}
	
	// Runs after every test
	@AfterMethod
	public void tearDown()
	{
		driver.close();
		driver.quit();
	}

}
