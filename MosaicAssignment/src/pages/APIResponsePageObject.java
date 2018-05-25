package pages;

import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.gson.Gson;

public class APIResponsePageObject {
	
	WebDriver driver;
	String responseURL = "http://api.fixer.io/latest?base=GBP";
	
	public Map<String, Object> getRateDataFromAPI() throws ParseException {
		// Open the browser with api url
		driver.navigate().to(responseURL);
		
		// Get the response
		JSONParser parser = new JSONParser();
		WebElement body = driver.findElement(By.xpath("//pre"));
		String bodyText = body.getText();
		Object obj = parser.parse(bodyText);
		
		JSONObject jsonObject = (JSONObject) obj;
		JSONObject rateObject = (JSONObject) jsonObject.get("rates");
		
		// Convert the jsonobject to map
		Map<String, Object> apiMap = new Gson().fromJson(rateObject.toString(),Map.class);
		return apiMap;
	}
	
	// Constructor initialises the state of the driver
	public APIResponsePageObject(WebDriver driver) {
		this.driver = driver;
	}

}
