package base;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Locale;
import java.util.function.Supplier;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class WebUtilities {
	
		WebDriver driver;
		WebDriverWait wait;

	public WebUtilities(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		
	}
	
	
	public void waitForUrlContains(String partialUrl) {
	    wait.until(ExpectedConditions.urlContains(partialUrl));
	}
	
	public WebElement waitForElementToBeClickable(By locator) {
		
		
		return wait.until(ExpectedConditions.elementToBeClickable(locator));

				
		
	}
	
	public void scrollDown() {
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 700);");
	}
	
	public WebElement waitForElementToBeVisible(By locator) {
	    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	

	
	public void waitForAttributeToBePresent(WebElement element, String attribute) {

	    wait.until(driver -> {
	        String value = element.getAttribute(attribute);
	        return value != null && !value.isEmpty();
	    });
	}
	public void safeClick(By locator) {
		
		

	    for(int i=0;i<3;i++) {

	        try {

	            WebElement element =
	                    wait.until(ExpectedConditions.elementToBeClickable(locator));

	            element.click();

	            return;

	        } catch(Exception e) {

	        }

	    }

	    throw new RuntimeException("Unable to click " + locator);
	}
	
	public void waitForValue(By locator, String expectedValue) {
	    wait.until(ExpectedConditions.attributeToBe(locator, "value", expectedValue));
	}
	public WebElement waitForElementToAppear(By locator) {
		
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

				
		
	}
	
	public boolean waitForTextToBePresent(By locator, String text) {
	    return wait.until(
	        ExpectedConditions.textToBePresentInElementLocated(locator, text)
	    );
	}
	
	public void waitUntilOptionsLoaded(WebElement dropdown) {
	    wait.until(driver -> new Select(dropdown).getOptions().size() > 1);
	}
	
	public WebElement waitForPresenceOfElement(By locator) {
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public Boolean waitForInvisibilityOfElement(By locator) {
		
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	public Boolean waitForNumberOfWindows(Integer number) {
		
		return wait.until(ExpectedConditions.numberOfWindowsToBe(number));
	}
	
	
	public String formatDateForUI(String date, Locale locale) {

	    LocalDate localDate = LocalDate.parse(date);

	    DateTimeFormatter formatter =
	            DateTimeFormatter.ofPattern("EEE dd MMM yyyy", locale);

	    String formattedDate = localDate.format(formatter);

	    if (locale.equals(new Locale("ms", "MY"))) {
	        formattedDate = formattedDate.replace("Ahd", "Aha");
	    }

	    return formattedDate;
	}
	
	public static Locale getLocale(String language) {

	    if (language.equals("Bahasa Malaysia")) {
	        return new Locale("ms", "MY");
	    }

	    if (language.equals("English")) {
	        return Locale.ENGLISH;
	    }

	    return Locale.ENGLISH;
	}
	
	
	   public static String getAdultText(String language) {

	        if (language.equalsIgnoreCase("Bahasa Malaysia")) {
	            return "dewasa";
	        }

	        return "adults";
	    }

	    public static String getChildText(String language) {

	        if (language.equalsIgnoreCase("Bahasa Malaysia")) {
	            return "kanak-kanak";
	        }

	        return "child";
	    }

	    public static String getRoomText(String language) {

	        if (language.equalsIgnoreCase("Bahasa Malaysia")) {
	            return "bilik";
	        }

	        return "rooms";
	    }
	    
	    public String readJsonFile(String fileName) throws IOException {

	        return FileUtils.readFileToString(
	            new File(System.getProperty("user.dir")
	                + "\\src\\test\\java\\resouces\\" + fileName),
	            StandardCharsets.UTF_8
	        );
	        
	        
	    }
	    public String getPriceLabel(String priceType, String language) throws IOException {

	        String jsonContent = readJsonFile("price-label.json");

	        ObjectMapper mapper = new ObjectMapper();

	        Map<String, Map<String, String>> labels = mapper.readValue(
	            jsonContent,
	            new TypeReference<Map<String, Map<String, String>>>() {}
	        );

	        String languageKey = language;

	        if (language.equalsIgnoreCase("English (US)")) {
	            languageKey = "English";
	        }

	        Map<String, String> languageLabels = labels.get(languageKey);

	        if (languageLabels == null) {
	            throw new IllegalArgumentException(
	                "No price labels found for language: " + language
	                + ". Expected JSON key: " + languageKey
	            );
	        }

	        String priceLabel = languageLabels.get(priceType);

	        if (priceLabel == null) {
	            throw new IllegalArgumentException(
	                "No price label found for price type: " + priceType
	                + " in language: " + languageKey
	            );
	        }

	        return priceLabel;
	    }

}
