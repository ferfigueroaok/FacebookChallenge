package Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class MainPage {

	private WebDriver driver;	
	
	public MainPage(WebDriver driver) {	
		
		this.driver=driver;		
	}

	private List<WebElement> AllPosts(){
		return driver.findElements(By.xpath("//div[contains(text(),'Status')]"));
	}	
	
	public void FindPost(String message) {
		
		driver.findElement(By.xpath("//div[text()='"+message+ "']"));
	}
	
	public WebElement GetLatestPost() {
		
		int i = AllPosts().size() -1;
		return AllPosts().get(i);			
	}

	public boolean CheckIfExistPost(String status) {

		boolean exist = false;		

		try {
			driver.findElement(By.xpath("//div[text()='"+status+ "']"));
			exist = true;
		}
		catch (Exception e) {
			exist = false;
		}
		
		return exist;
	}
}
