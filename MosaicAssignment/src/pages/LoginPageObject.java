package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPageObject {
	
	WebDriver driver;
	
	// Methods to fill in login details
	public void setUserName(String userName) {
		driver.findElement(By.xpath("//*[@placeholder='username']")).sendKeys(userName);
	}
	
	public void setPassword(String password) {
		driver.findElement(By.xpath("//*[@type='password']")).sendKeys(password);
	}
	
	public void clickSubmit() {
		driver.findElement(By.xpath("//*[@type='submit']")).click();
	}
	
	public boolean usernameFieldPresent()
	{
		return driver.findElement(By.xpath("//*[@placeholder='username']")).isDisplayed();
	}
	
	public boolean passwordFieldPresent()
	{
		return driver.findElement(By.xpath("//*[@type='password']")).isDisplayed();
	}
	
	public boolean SubmitButtonPresent()
	{
		return driver.findElement(By.xpath("//*[@type='submit']")).isDisplayed();
	}
	
	// Constructor initialises the state of the driver
	public LoginPageObject(WebDriver driver)
	{
		this.driver = driver;
	}

}
