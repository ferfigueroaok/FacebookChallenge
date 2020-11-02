package Exercise;

import static io.restassured.path.json.JsonPath.from;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import Pages.LoginPage;
import Pages.MainPage;
import Useful.DataConfig;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

public class FacebookTestSetting {

	protected String accessToken;
	protected String url;
	protected ChromeDriver driver;
	protected LoginPage loginPage;
	protected MainPage mainPage;
	protected JSONObject jsonData;

	@BeforeTest
	public void setUp() throws FileNotFoundException, IOException, ParseException {		
				
		//CHROME DRIVER SETTINGS
		jsonData = DataConfig.GetJson("./settings.json");
		
		System.setProperty("webdriver.chrome.driver", jsonData.get("chromePath").toString());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);
		driver.get(jsonData.get("pageUrl").toString());
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;

		loginPage = new LoginPage(driver);		
		mainPage = new MainPage(driver);	
		
		//RESTASSURED SETTINGS
		RestAssured.baseURI = jsonData.get("baseURI").toString();
		RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
		RestAssured.requestSpecification = new RequestSpecBuilder()
				.setContentType(ContentType.JSON)
				.build();
		
		//AppToken
		String response = RestAssured.given()
				.param("client_id", jsonData.get("appID").toString())
				.param("client_secret", jsonData.get("secretKey").toString())
				.param("grant_type", "client_credentials")
				.get("oauth/access_token").asString();
		accessToken = from(response).getString("access_token");
		
		//User token
		response = RestAssured.given()
				.param("access_token", accessToken)
				.get(jsonData.get("appID").toString()+"/accounts")
				.then()
				.extract().asString();		
		
		accessToken = from(response).getString("data[0].access_token");
		String userId = from(response).getString("data[0].id");		
		
		//Page token 
		response = RestAssured.given()
				.param("access_token", accessToken)
				.get(userId+"/accounts")
				.then().extract().asString();
		
		accessToken = from(response).getString("data[0].access_token");
	}
	
	@AfterTest
	public void tearDown() {
		driver.close();
	}
}
