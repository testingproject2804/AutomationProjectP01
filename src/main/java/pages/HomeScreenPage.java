package pages;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.Select;


import base.WebUtilities;


public class HomeScreenPage extends WebUtilities {
	
	WebDriver driver;
	

	public HomeScreenPage(WebDriver driver) {
		super(driver);
	    this.driver = driver;
	    PageFactory.initElements(driver, this);
	    
	}
	


	private By destinationBox = By.id("searchbox-horizontal-destination-input");
	private By guestButton = By.xpath("//button[@data-testid='occupancy-config']");
	private By adultCount = By.xpath("//input[@id='group_adults']");
	private By plusAdults = By.xpath("//input[@id='group_adults']/following-sibling::button[last()]");
	private By selectDates = By.cssSelector("[data-testid='searchbox-dates-container']");
	private By plusKids = By.xpath( "//input[@id='group_children']/following-sibling::button[last()]");
	private By kidCount  = By.xpath("//input[@id='group_children']");
	private By age = By.name("age");
	private final By roomCount = By.xpath("//input[@id='no_rooms']");
	private final By roomPlusButton = By.xpath("//input[@id='no_rooms']/following-sibling::button[last()]");
	private By nextMonth = By.xpath("//button[.//svg[@data-rtl-flip='true']]");
	

	@FindBy(xpath="//div[@data-testid='searchbox-alert']")
	WebElement destinationError;
	@FindBy(xpath="//button[@type='submit']")
	WebElement submit;

	
    public void enterDestination(String destination) {



        WebElement destinationInputBox =
                waitForElementToBeClickable(destinationBox);

        destinationInputBox.click();
        destinationInputBox.clear();
        destinationInputBox.sendKeys(destination);

        // Wait for autocomplete results to appear
        By autocomplete = By.xpath("//div[@data-testid='autocomplete-result']");
        waitForElementToAppear(autocomplete);

        List<WebElement> suggestions =
                driver.findElements(autocomplete);

        for (WebElement suggestionElement : suggestions) {
            System.out.println("Suggestion: " + suggestionElement.getText());
        }

        By suggestion = By.xpath(
            "//div[@data-testid='autocomplete-result']//div[normalize-space()='"
            + destination + "']"
        );

        WebElement userInput =
                waitForElementToBeClickable(suggestion);

        userInput.click();
	}
    public void selectDates(String checkinDate, String checkOutDate, String language) {

        WebElement datePicker = waitForElementToBeClickable(selectDates);

        String expanded = datePicker.getAttribute("aria-expanded");

        System.out.println("Date picker before handling: " + expanded);

        if ("false".equals(expanded)) {
            datePicker.click();
        }

        System.out.println("Date picker after handling: "
                + datePicker.getAttribute("aria-expanded"));

        Map<String, String> nextMonthLabels = Map.of(
        	    "English (US)", "Next month",
        	    "Bahasa Malaysia", "Bulan depan"
        	);

        	String nextMonthLabel = nextMonthLabels.get(language);

        	By nextMonthButton = By.xpath(
        	    "//button[@aria-label='" + nextMonthLabel + "']");
        	
        	if (nextMonthLabel == null) {
        	    throw new IllegalArgumentException(
        	        "Unsupported language: " + language
        	    );
        	}

        // Calculate how many months to move
        LocalDate today = LocalDate.now();
        LocalDate checkIn = LocalDate.parse(checkinDate);

        int monthsToMove =
                (checkIn.getYear() - today.getYear()) * 12
                + (checkIn.getMonthValue() - today.getMonthValue());

        System.out.println("Months to move: " + monthsToMove);

        for (int i = 0; i < monthsToMove; i++) {
            WebElement nextMonth =
                    waitForElementToBeClickable(nextMonthButton);

            nextMonth.click();
        }

        By checkInLocator = By.xpath(
                "//span[@data-date='" + checkinDate + "']"
        );

        WebElement checkInDate =
                waitForElementToBeClickable(checkInLocator);

        checkInDate.click();

        By checkOutLocator = By.xpath(
                "//span[@data-date='" + checkOutDate + "']"
        );

        WebElement checkoutDate =
                waitForElementToBeClickable(checkOutLocator);

        checkoutDate.click();
    }
    
    public void openGuestSelector() {
    	WebElement guest =waitForElementToBeClickable(guestButton);
    	guest.click();

    }
    
    private void openGuestPopupIfClosed(String guestType) {

        By locator;

        switch (guestType) {
            case "adult":
                locator = adultCount;
                break;

            case "child":
                locator = kidCount;
                break;

            case "room":
                locator = roomCount;
                break;

            default:
                throw new IllegalArgumentException("Invalid guest type: " + guestType);
        }

        try {
            waitForPresenceOfElement(locator);
        } catch (Exception e) {
            safeClick(guestButton);
            waitForPresenceOfElement(locator);
        }
    }
    
    public int getAdultCount() {
        return Integer.parseInt(driver.findElement(adultCount).getAttribute("value"));
    }

	
	public void noOfAdult(int numOfAdults) {
		
		openGuestPopupIfClosed("adult");
		
		while(getAdultCount() < numOfAdults) {
	
			 int previous = getAdultCount();   

		        safeClick(plusAdults);

		        waitForValue(adultCount, String.valueOf(previous + 1)); 
			    }
    
      }

      private int getKidsCount() {

	    return Integer.parseInt(driver.findElement(kidCount).getAttribute("value"));
	            
	}
    public void noOfKids(int numOfKids) {
	
	
    	openGuestPopupIfClosed("child");
		 while(getKidsCount() < numOfKids) {
			 int previous = getKidsCount();   

		     safeClick(plusKids);
		     waitForValue(kidCount, String.valueOf(previous + 1));
		     

		 
			    }	
		 WebElement ageDropdown = waitForElementToAppear(age);
		

		 waitUntilOptionsLoaded(ageDropdown);

		 new Select(ageDropdown).selectByValue("2");

		
		
	}


	private int getRoomCount() {
	
		
		 WebElement room = driver.findElement(roomCount);
		 return Integer.parseInt(room.getAttribute("value"));


	}

	public void noOfRooms(int numOfRooms) {

	    openGuestPopupIfClosed("room");

	    int currentRooms = getRoomCount();

	    while (currentRooms < numOfRooms) {

	        safeClick(roomPlusButton);

	        int expectedRooms = currentRooms + 1;

	        waitForValue(roomCount, String.valueOf(expectedRooms));

	        currentRooms = expectedRooms;
	    }
	}
	
	public boolean isDestinationErrorDisplayed() {
	    return waitForElementToAppear(
	        By.xpath("//div[@data-testid='searchbox-alert']")
	    ).isDisplayed();
	}
	
	public String getDestinationErrorMessage() {
	    return waitForElementToAppear(
	        By.xpath("//div[@data-testid='searchbox-alert']")
	    ).getText();
	}
	
	
	
    public SearchResultPage clickSearchBtn() {
    	
    	submit.click();
    	return new SearchResultPage(driver);
    }
	
	
	
	

}
