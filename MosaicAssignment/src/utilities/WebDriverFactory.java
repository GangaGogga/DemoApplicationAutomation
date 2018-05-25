package utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebDriverFactory {
	
	public static WebDriver Open(String browserType)
	{
		if(browserType.equalsIgnoreCase("chrome"))
		{
			return new ChromeDriver();
		} 
		else if (browserType.equalsIgnoreCase("firefox"))
		{
			return new FirefoxDriver();
		}
		else if (browserType.equalsIgnoreCase("ie"))
		{
			return new InternetExplorerDriver();
		}
		else
		{
			return new ChromeDriver();
		}
	}

}
