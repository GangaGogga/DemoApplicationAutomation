package pages;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;

public class DashboardPageObject {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public String getDashboardText() {
		return driver.findElement(By.className("h2")).getText();
	}
	
	public boolean dashboardTextVisible()
	{
		return driver.findElement(By.className("h2")).isDisplayed();
	}
	
	public void clickSignout()
	{
		// Open the dashboard page and then signout 
		driver.navigate().to("http://mosaic-test-app.s3-website.eu-west-2.amazonaws.com/dashboard.html?#");
		driver.findElement(By.xpath("//*[@class='nav-link']")).click();
	}
	
	public WebElement getTable() {
		// Explicit wait until the entire table with AUD & other currencies are loaded 
		wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(), 'AUD')]")));
		System.out.println(driver.findElement(By.xpath("//td[contains(text(), 'AUD')]")).getText());
		
		// Once the table is reloaded, locate it
		WebElement table = driver.findElement(By.xpath("//*[@class='table table-striped table-sm']"));
		return table;
	}
	
	public List<WebElement> getActualRows() {
		// Get all the rows from body ignoring the header row
		return getTable().findElements(By.xpath("//tbody/tr"));
	}
	
	public List<WebElement> getHeaderColumns() {
		// Get the header column
		return getTable().findElements(By.xpath("//th"));
	}
	
	public Map<String, Object> addRowDataToMap() {
		// Add each row in a key value pair to a JSONObject
		JSONObject obj = new JSONObject();
		for (WebElement row : getActualRows()) {
			  List<WebElement> cells = row.findElements(By.tagName("td"));
			  String key = "";
			  String value = "";
			  for (int i = 0; i < cells.size(); i++)
			  {
				  if (i%2 == 0) {
					  key = cells.get(i).getText();
				  } else {
					  value = cells.get(i).getText();
				  }
			  }
			  obj.put(key, value);
		 }
		// Using GSON library convert the JSONObject to a Map
		Map<String, Object> tableMap = new Gson().fromJson(obj.toString(),Map.class);
		return tableMap;
	}
	
	// Constructor initialises the state of the driver
	public DashboardPageObject(WebDriver driver) {
		this.driver = driver;
	}

}
