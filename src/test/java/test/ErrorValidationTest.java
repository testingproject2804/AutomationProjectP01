package test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.BookingConfirmationPage;
import pages.HomeScreenPage;
import pages.HotelDetailsPage;
import pages.SearchResultPage;
import org.testng.annotations.Listeners;

@Listeners(base.Listeners.class)
public class ErrorValidationTest extends BaseTest{

	
	@Test(timeOut = 140000 ,dataProvider = "getDestinationData",groups = "Booking", description = "Verify error validation related to destination")
	public void verifyMissingDestination(HashMap<String,String> data) throws IOException  {
		
		HomeScreenPage homePage = landingPage.initializeHomePage(data.get("currency"), data.get("language"));
		homePage.clickSearchBtn();
		Assert.assertTrue(homePage.isDestinationErrorDisplayed());
		
	}
	
	@Test(timeOut = 140000 ,dataProvider = "getMissingGuestData",groups = "Booking", description = "Verify error validation related to mandatory field")
	public void verifyMissingGuestDetailsValidation(HashMap<String,String> data) throws IOException {
		
		HomeScreenPage homePage = landingPage.initializeHomePage(data.get("currency"), data.get("language"));
    	homePage.enterDestination(data.get("destination"));
    	homePage.selectDates(data.get("checkIn"), data.get("checkOut"), data.get("language"));
    	homePage.noOfAdult(Integer.parseInt(data.get("adults")));
    	homePage.noOfKids(Integer.parseInt(data.get("children")));
    	homePage.noOfRooms(Integer.parseInt(data.get("rooms")));
    	
    	
    	SearchResultPage searchResults = homePage.clickSearchBtn();
    	
    	Assert.assertTrue(searchResults.isResultDisplayed(data.get("destination")));
    	searchResults.closeMap();
    	Assert.assertTrue(searchResults.isHotelNameDisplayed(data.get("hotel")));
    	
    	HotelDetailsPage hotelDetailPage = searchResults.clickHotelTitle(data.get("hotel"));
    	hotelDetailPage.viewRooms();
    	hotelDetailPage.selectRooms(Integer.parseInt(data.get("rooms")));
    	BookingConfirmationPage bookingConfirmation = hotelDetailPage.clickSelect();
		bookingConfirmation.clickNext();
		bookingConfirmation.validateMandatoryFieldErrors();
	   
	}
	
   @Test(timeOut = 140000 , dataProvider = "getInvalidEmailData",groups = "Booking", description = "Verify error validation related to invalid email")
	public void verifyInvalidEmailValidation(HashMap<String,String> data) throws IOException {
		
		HomeScreenPage homePage = landingPage.initializeHomePage(data.get("currency"), data.get("language"));
    	homePage.enterDestination(data.get("destination"));
    	homePage.selectDates(data.get("checkIn"), data.get("checkOut"), data.get("language"));
    	homePage.noOfAdult(Integer.parseInt(data.get("adults")));
    	homePage.noOfKids(Integer.parseInt(data.get("children")));
    	homePage.noOfRooms(Integer.parseInt(data.get("rooms")));
    	
    	
    	SearchResultPage searchResults = homePage.clickSearchBtn();
    	
    	Assert.assertTrue(searchResults.isResultDisplayed(data.get("destination")));
    	searchResults.closeMap();
    	Assert.assertTrue(searchResults.isHotelNameDisplayed(data.get("hotel")));
    	
    	HotelDetailsPage hotelDetailPage = searchResults.clickHotelTitle(data.get("hotel"));
    	hotelDetailPage.viewRooms();
    	hotelDetailPage.selectRooms(Integer.parseInt(data.get("rooms")));
    	BookingConfirmationPage bookingConfirmation = hotelDetailPage.clickSelect();
		bookingConfirmation.enterGuestDetails(data.get("firstName"), data.get("lastName"), data.get("email"), data.get("address"),data.get("city"),data.get("phoneNo"));
		bookingConfirmation.validateInvalidEmail();
		
	}
	@Test(timeOut = 140000 ,dataProvider = "getInvalidPhoneData",groups = "Booking", description = "Verify error validation related to invalid phone")
	public void verifyInvalidPhoneNoValidation(HashMap<String,String> data) throws IOException {
		
		HomeScreenPage homePage = landingPage.initializeHomePage(data.get("currency"), data.get("language"));
    	homePage.enterDestination(data.get("destination"));
    	homePage.selectDates(data.get("checkIn"), data.get("checkOut"), data.get("language"));
    	homePage.noOfAdult(Integer.parseInt(data.get("adults")));
    	homePage.noOfKids(Integer.parseInt(data.get("children")));
    	homePage.noOfRooms(Integer.parseInt(data.get("rooms")));
    	
    	
    	SearchResultPage searchResults = homePage.clickSearchBtn();
    	
    	Assert.assertTrue(searchResults.isResultDisplayed(data.get("destination")));
    	searchResults.closeMap();
    	Assert.assertTrue(searchResults.isHotelNameDisplayed(data.get("hotel")));
    	
    	HotelDetailsPage hotelDetailPage = searchResults.clickHotelTitle(data.get("hotel"));
    	hotelDetailPage.viewRooms();
    	hotelDetailPage.selectRooms(Integer.parseInt(data.get("rooms")));
    	BookingConfirmationPage bookingConfirmation = hotelDetailPage.clickSelect();
		bookingConfirmation.enterGuestDetails(data.get("firstName"), data.get("lastName"), data.get("email"), data.get("address"),data.get("city"),data.get("phoneNo"));
		bookingConfirmation.validateInvalidPhoneNo();
		
	}
	@Test(timeOut = 140000 ,dataProvider = "getInvalidGuestData",groups = "Booking", description = "Verify error validation related to invalid phone")
	public void verifyInvalidGuestFieldDatatypeValidation(HashMap<String,String> data) throws IOException {
		
		HomeScreenPage homePage = landingPage.initializeHomePage(data.get("currency"), data.get("language"));
    	homePage.enterDestination(data.get("destination"));
    	homePage.selectDates(data.get("checkIn"), data.get("checkOut"), data.get("language"));
    	homePage.noOfAdult(Integer.parseInt(data.get("adults")));
    	homePage.noOfKids(Integer.parseInt(data.get("children")));
    	homePage.noOfRooms(Integer.parseInt(data.get("rooms")));
    	
    	
    	SearchResultPage searchResults = homePage.clickSearchBtn();
    	
    	Assert.assertTrue(searchResults.isResultDisplayed(data.get("destination")));
    	searchResults.closeMap();
    	Assert.assertTrue(searchResults.isHotelNameDisplayed(data.get("hotel")));
    	
    	HotelDetailsPage hotelDetailPage = searchResults.clickHotelTitle(data.get("hotel"));
    	hotelDetailPage.viewRooms();
    	hotelDetailPage.selectRooms(Integer.parseInt(data.get("rooms")));
    	BookingConfirmationPage bookingConfirmation = hotelDetailPage.clickSelect();
		bookingConfirmation.enterGuestDetails(data.get("firstName"), data.get("lastName"), data.get("email"), data.get("address"),data.get("city"),data.get("phoneNo"));
		bookingConfirmation.validateInvalidDataTypeErrors();
		
	}
	
	
	
	
	
	@DataProvider
    public Object[][] getDestinationData() throws IOException {
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") +"\\src\\test\\java\\resouces\\ErrorValidationData.json");
    	 return new Object[][] {{data.get(0)}};
    	 
	}
	
	@DataProvider
	public Object[][] getMissingGuestData() throws IOException {

	    List<HashMap<String, String>> data =
	            getJsonDataToMap(System.getProperty("user.dir")
	                    + "\\src\\test\\java\\resouces\\ErrorValidationData.json");

	    return new Object[][] {{data.get(1)}};
	}
	
@DataProvider
	public Object[][] getInvalidEmailData() throws IOException {

	    List<HashMap<String, String>> data =
	            getJsonDataToMap(System.getProperty("user.dir")
	                    + "\\src\\test\\java\\resouces\\ErrorValidationData.json");

	    return new Object[][] {{data.get(2)}};
	}
	
	@DataProvider
	public Object[][] getInvalidPhoneData() throws IOException {

	    List<HashMap<String, String>> data =
	            getJsonDataToMap(System.getProperty("user.dir")
	                    + "\\src\\test\\java\\resouces\\ErrorValidationData.json");

	    return new Object[][] {{data.get(3)}};
	}
	
	@DataProvider
	public Object[][] getInvalidGuestData() throws IOException {

	    List<HashMap<String, String>> data =
	            getJsonDataToMap(System.getProperty("user.dir")
	                    + "\\src\\test\\java\\resouces\\ErrorValidationData.json");

	    return new Object[][] {{data.get(4)}};
	}
	


}
