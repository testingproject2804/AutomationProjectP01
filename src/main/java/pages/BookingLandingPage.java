package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.WebUtilities;

public class BookingLandingPage extends WebUtilities{
	
	WebDriver driver;
	private By dismissPopup = By.cssSelector("button[aria-label='Dismiss sign-in info.']");
	private By currencyButton = By.xpath("//button[@data-testid='header-currency-picker-trigger']");
	private By languageButton = By.xpath("//button[@data-testid='header-language-picker-trigger']");

	public BookingLandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public void dismissPopUp() {

	    
	   WebElement dismiss = waitForElementToBeClickable(dismissPopup);
	   dismiss.click();
	    
	    		
	
	}
	
	
	
	
	
	public void selectCurrency(String currency) {
		
       WebElement currButton = waitForElementToBeClickable(currencyButton);
       
       currButton.click();

		
       By currencyOption = By.xpath("//button[@data-testid='selection-item']//div[text()='" + currency + "']");
	   WebElement optionCurrency = waitForElementToBeClickable(currencyOption);		
	   optionCurrency.click();
		    		
		   

	   
		}
	
	public void selectLanguage(String language) {

	    WebElement lanButton = waitForElementToBeClickable(languageButton);
	    lanButton.click();

	    System.out.println("Language dropdown opened. Looking for: " + language);

	    // Check what language options are currently present
	    List<WebElement> languageOptions =
	            driver.findElements(By.cssSelector("button[data-testid='selection-item']"));

	    System.out.println("Immediately after click - options found: "
	            + languageOptions.size());

	    for (WebElement option : languageOptions) {
	        System.out.println("Option text: [" + option.getText() + "]");
	    }

	    By languageOption = By.xpath(
	            "//button[@data-testid='selection-item'][contains(normalize-space(.),'"
	                    + language + "')]"
	    );

	    WebElement langOption = waitForElementToBeClickable(languageOption);
	    langOption.click();

	    System.out.println("Language button clicked.");
	}

	
	
	public HomeScreenPage initializeHomePage(String currency, String language) {
	    dismissPopUp();
	    selectCurrency(currency);
	    selectLanguage(language);
	    return new HomeScreenPage(driver);
		
		
	}
	
	
	
	
	
	

}
