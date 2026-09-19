package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.WebUtilities;

public class SearchResultPage extends WebUtilities {
	
	WebDriver driver;
	
	

	
	public SearchResultPage(WebDriver driver) {
		
		super(driver);
	    this.driver = driver;
	    PageFactory.initElements(driver, this);

	}
	
	
	@FindBy(tagName ="h1")
	WebElement heading;
	
    private By closeMapBy =
            By.xpath("//div[@data-testid='map-overlay-container']/button");

    public boolean isResultDisplayed(String destination) {

        By headingLocator = By.tagName("h1");

        return waitForTextToBePresent(headingLocator, destination);
    }
	
	public void closeMap() {
	    if (!driver.findElements(closeMapBy).isEmpty()) {
	        WebElement closeMap = waitForElementToBeClickable(closeMapBy);
	        closeMap.click();
	        waitForInvisibilityOfElement(closeMapBy);
	    }
	}
	

	
	public boolean isHotelNameDisplayed(String hotelName) {

	    By hotelLocator = By.xpath(
	        "//div[@data-testid='title' and normalize-space()=\"" 
	        + hotelName.trim() + "\"]"
	    );

	    try {
	        return new WebDriverWait(driver, Duration.ofSeconds(10))
	                .until(ExpectedConditions.visibilityOfElementLocated(hotelLocator))
	                .isDisplayed();

	    } catch (TimeoutException e) {
	        return false;
	    }
	}
	

	

	public HotelDetailsPage clickHotelTitle(String hotelName) {

	    By hotelLocator = By.xpath(
	        "//div[@data-testid='title' and normalize-space()='" + hotelName + "']"
	    );

	    String currentWindow = driver.getWindowHandle();

	    for (int attempt = 1; attempt <= 3; attempt++) {

	        try {

	            WebElement hotel = waitForElementToBeClickable(hotelLocator);

	            try {

	                hotel.click();

	            } catch (ElementClickInterceptedException e) {

	                System.out.println(
	                    "Hotel click intercepted. Using JavaScript click."
	                );

	                ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].click();",
	                    hotel
	                );
	            }

	            break;

	        } catch (StaleElementReferenceException e) {

	            System.out.println(
	                "Hotel element became stale. Retrying... Attempt: "
	                + attempt
	            );

	            if (attempt == 3) {
	                throw e;
	            }
	        }
	    }

	    waitForNumberOfWindows(2);

	    for (String handle : driver.getWindowHandles()) {

	        if (!handle.equals(currentWindow)) {
	            driver.switchTo().window(handle);
	            break;
	        }
	    }

	    return new HotelDetailsPage(driver);
	}

	


	

}
