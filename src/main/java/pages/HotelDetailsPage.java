package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.WebUtilities;

public class HotelDetailsPage extends WebUtilities {
	
	WebDriver driver;
   
	

	public HotelDetailsPage(WebDriver driver) {
		super(driver);
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	}
	
	private By roomDropdown = By.cssSelector("select[data-testid='select-room-trigger']");
	private By rooms = By.xpath("//div[@data-component='hotel/new-rooms-table/reservation-cta']");
	private By reserveButton = By.cssSelector("button.js-reservation-button");
	
	public void viewRooms() {
	    System.out.println("Checking room section...");
	    waitForPresenceOfElement(roomDropdown);
	    System.out.println("Room dropdown is present.");
	}
	public void selectRooms(int numberOfRooms) {

	    System.out.println("Trying to find room dropdown...");

	    WebElement roomDropdownElement =
	            waitForPresenceOfElement(roomDropdown);

	    System.out.println("Room dropdown found again.");

	    Select roomSelect = new Select(roomDropdownElement);

	    System.out.println("Select object created.");

	    roomSelect.selectByValue(String.valueOf(numberOfRooms));

	    System.out.println("Room selection completed.");
	}
	public BookingConfirmationPage clickSelect() {

	    System.out.println("Checking reserve button...");

	    WebElement button = waitForElementToBeClickable(reserveButton);

	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", button);

	    System.out.println("About to click reserve button...");
	    button.click();
	    System.out.println("Reserve button click completed.");

	    return new BookingConfirmationPage(driver);
	}
}	
	

