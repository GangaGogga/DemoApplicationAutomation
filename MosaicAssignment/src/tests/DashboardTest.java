package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.DashboardPageObject;
import pages.LoginPageObject;
import pages.APIResponsePageObject;

public class DashboardTest {
	
	WebDriver driver;
	WebDriverWait wait;
	
	String errorMessage = "";
	String webUrl = "http://mosaic-test-app.s3-website.eu-west-2.amazonaws.com/dashboard.html?";
	
	LoginPageObject loginPage;
	DashboardPageObject dashboardPage;
	APIResponsePageObject responsePage;
	
	/* Test to check row count with api data to validate table is displaying all 
	 * currencies from api 
	 * This test will fail currently as not all the currency - rate is fetched from api */
	@Test
	public void assertRowCountWithAPIDataSize() throws Exception
	{
		try {
			int actualRowCount = dashboardPage.getActualRows().size();
			responsePage = new APIResponsePageObject(driver);
			int expectedRowCount = responsePage.getRateDataFromAPI().size();
			errorMessage = "The table row count doesnt match with api rate data count. Not all the currencies are fetched in the table from api";
			Assert.assertEquals(actualRowCount, expectedRowCount, errorMessage);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	/* Test to check that the rate table doesn't have any empty cell */
	@Test
	public void assertNoEmptyCell()
	{
		errorMessage = "The test failed as the table has a empty cell";
		List<WebElement> allRows = dashboardPage.getActualRows();
		for (WebElement row : allRows) {
			  List<WebElement> cells = row.findElements(By.tagName("td"));
			  for (WebElement cell : cells) {
			       String cellContent = cell.getText();
			       boolean emptyCell = cellContent.isEmpty();
			       Assert.assertFalse(emptyCell, errorMessage);
			   }
		}
	}
		
	/* Test to validate column count with hardcoded expected value */
	@Test
	public void assertColumnCount()
	{
		int expectedColumnCount = 2;
		int actualColumnCount = dashboardPage.getHeaderColumns().size();
		errorMessage = "The table column count is incorrect";
		Assert.assertEquals(actualColumnCount, expectedColumnCount, errorMessage);
	}
	
	/* Test to validate that rate for each currency in table matches with the 
	 * rate of the corresponding currency in api */
	@Test
	public void assertRateInTableMatchesAPI() throws ParseException
	{
		// Table Data
		Map<String, Object> tableMap = dashboardPage.addRowDataToMap();
		// API Data
		responsePage = new APIResponsePageObject(driver);
		Map<String, Object> apiMap = responsePage.getRateDataFromAPI();
		
		// outer loop iterating through table 
		for (Map.Entry<String,Object> entry : tableMap.entrySet()) 
		{
			// inner loop iterating through api data
            for (Map.Entry<String,Object> apientry : apiMap.entrySet()) 
            {
            		// condition to verify currency in table matches with api
            		if (entry.getKey().equals(apientry.getKey()))
            		{
            			System.out.println("TableValue: " + entry.getValue() + " APIValue: " + apientry.getValue());
            			errorMessage = "The currency rate in the table for " + entry.getKey() + " is " + entry.getValue() + " but the api value is " + apientry.getValue();
            			// Assertion to check the rates for currency is same in table and api
            			Assert.assertEquals(entry.getValue().toString(), apientry.getValue().toString(), errorMessage);
            		}
            }
		}
	}
	
	/* Test to validate that currency for rate in table matches with the 
	 * currency of the corresponding rate in api */
	@Test
	public void assertCurrencyInTableMatchesAPI() throws ParseException
	{
		// Table Data
		Map<String, Object> tableMap = dashboardPage.addRowDataToMap();
		// API Data
		responsePage = new APIResponsePageObject(driver);
		Map<String, Object> apiMap = responsePage.getRateDataFromAPI();
		
		// outer loop iterating through table 
		for (Map.Entry<String,Object> entry : tableMap.entrySet()) 
		{
			// inner loop iterating through api data
            for (Map.Entry<String,Object> apientry : apiMap.entrySet()) 
            {
            		// condition to verify currency in table matches with api
            		if (entry.getValue().equals(apientry.getValue()))
            		{
            			System.out.println("TableKey: " + entry.getKey() + " APIKey: " + apientry.getKey());
            			errorMessage = "The currency in the table for " + entry.getValue() + " is " + entry.getKey() + " but in api is " + apientry.getKey();
            			// Assertion to check the currency is same in table and api for rates
            			Assert.assertEquals(entry.getKey().toString(), apientry.getKey().toString(), errorMessage);
            		}
            }
		}
	}
	
	// Runs before every test
	@BeforeMethod
	public void setUp()
	{
		driver = utilities.WebDriverFactory.Open("chrome");
		driver.get(webUrl);
		dashboardPage = new DashboardPageObject(driver);
		
	}
		
	// Runs after every test
	@AfterMethod
	public void tearDown()
	{
		driver.close();
		driver.quit();
	}
	

}
