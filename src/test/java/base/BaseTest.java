package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.BookingLandingPage;



public class BaseTest {
	
	public WebDriver driver;
	public BookingLandingPage landingPage;
	public WebDriver initializeDriver() throws IOException {

	    Properties property = new Properties();
	    FileInputStream fileInput = new FileInputStream(
	        System.getProperty("user.dir")
	        + "/src/main/java/resources/GlobalData.properties"
	    );

	    property.load(fileInput);

	    String browserName = property.getProperty("browser");

	    if (browserName.equalsIgnoreCase("chrome")) {

	        WebDriverManager.chromedriver().setup();

	        ChromeOptions options = new ChromeOptions();

//	        options.addArguments("--headless=new");
//	        options.addArguments("--disable-gpu");
//	        options.addArguments("--window-size=1920,1080");
//	        options.addArguments("--no-sandbox");
//	        options.addArguments("--disable-dev-shm-usage");
//	        options.addArguments("--disable-extensions");
//	        options.addArguments("--disable-notifications");

	        driver = new ChromeDriver(options);

	    

	    } else if (browserName.equalsIgnoreCase("edge")) {

	        WebDriverManager.edgedriver().setup();
	        driver = new EdgeDriver();

	    } else if (browserName.equalsIgnoreCase("firefox")) {

	        WebDriverManager.firefoxdriver().setup();
	        driver = new FirefoxDriver();
	    }

	    driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));

	    return driver;
	}
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
    	String jsonContent = FileUtils.readFileToString(new File(filePath),
		StandardCharsets.UTF_8);

       ObjectMapper mapper = new ObjectMapper();

       List<HashMap<String, String>> data = mapper.readValue(
            jsonContent,
            new TypeReference<List<HashMap<String, String>>>() {
            });
    
        return data;
    
    }  
    


    @BeforeMethod
    public BookingLandingPage launchPage() throws IOException {
    	driver = initializeDriver();
    	driver.get("https://www.booking.com/");
		
	    landingPage = new BookingLandingPage(driver);
	
		return landingPage;
    }
    
    @AfterMethod
    public void tearDown() {
    	if(driver != null) {
    		driver.quit();
    	}
    }


	

}
