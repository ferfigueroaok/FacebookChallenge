package Exercise;

import static io.restassured.path.json.JsonPath.from;

import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.User;
import io.restassured.RestAssured;


public class FacebookTest extends FacebookTestSetting {
	
	private String messsageID;
	private String statusToCreate;
	private String statusToUpdate;
	
	@Test (priority= 1, description = "Create post")
	public void CreatePostTest() {		
			
		//API POST
		FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
		User me = fbClient.fetchObject("me", User.class);
		
		int randomNumber =  new Random().nextInt(9999) + 1000;
		statusToCreate = "Status updated with number " + String.valueOf(randomNumber);
		String response = RestAssured.post(me.getId().toString()+"/feed?message="+statusToCreate+"&access_token="+accessToken).then().extract().asString();	
		messsageID = from(response).get("id");
		
		//UI VALIDATION		
		loginPage.Login("vzbharaqmi_1604262476@tfbnw.net", "T3st1ng!");
		mainPage.FindPost(statusToCreate);		
		
		String lastPost = mainPage.GetLatestPost().getText();
		Assert.assertEquals(lastPost, statusToCreate);

	}
	
	@Test(priority= 2, description = "Update post")
	public void UpdatePostTest()  {
		
		//API UPDATE				
		int randomNumber =  new Random().nextInt(9999) + 1000;
		statusToUpdate = "Status created with number " + String.valueOf(randomNumber);
		RestAssured.post(messsageID+"?message="+statusToUpdate+"&access_token="+accessToken).then().extract().asString();	
		
		//UI VALIDATION	
		driver.navigate().refresh();
		mainPage.FindPost(statusToUpdate);			
		
		String lastPost = mainPage.GetLatestPost().getText();
		Assert.assertEquals(lastPost, statusToUpdate);
	}
	
	@Test(priority= 3, description = "Delete post")
	public void DeletePostTest()  {		
	
		//API DELETE
		RestAssured.delete(messsageID+"?access_token="+accessToken).then().extract().asString();	

		//UI VALIDATION	
		driver.navigate().refresh();
		boolean exist = mainPage.CheckIfExistPost(statusToUpdate);		
		
		Assert.assertFalse(exist);
		
	}
}
