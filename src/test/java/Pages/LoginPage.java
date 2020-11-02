package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

	private WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		
		this.driver = driver;
	}
	
	private WebElement UserInput() {
		return driver.findElement(By.id("email"));
	}
	private WebElement PassInput() {
		return driver.findElement(By.xpath("//input[@id='pass']"));
	}
	private WebElement LoginButton() {
		return driver.findElement(By.id("loginbutton"));
	}
	
	public void Login(String user, String pass){
		
		UserInput().sendKeys("vzbharaqmi_1604262476@tfbnw.net");
		PassInput().sendKeys("T3st1ng!");
		LoginButton().click();
		
	}
	
	
}
