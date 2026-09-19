package test;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.BookingConfirmationPage;
import pages.BookingLandingPage;
import pages.HomeScreenPage;
import pages.HotelDetailsPage;
import pages.SearchResultPage;
import org.testng.annotations.Listeners;

@Listeners(base.Listeners.class)

public class VerifyEndToEndBookingFlow extends BaseTest {
	
	
	@Test(timeOut = 120000, dataProvider = "getData",groups = "Booking", description = "Verify end-to-end hotel booking flow with valid booking data")
	public void verifyEndToEndBookingFlow(HashMap<String,String> data) throws IOException {
		
    	
    	HomeScreenPage homePage = landingPage.initializeHomePage(data.get("currency"), data.get("language"));
    	homePage.enterDestination(data.get("destination"));
    	homePage.selectDates(data.get("checkIn"), data.get("checkOut"),data.get("language"));
    	homePage.noOfAdult(Integer.parseInt(data.get("adults")));
    	homePage.noOfKids(Integer.parseInt(data.get("children")));
    	homePage.noOfRooms(Integer.parseInt(data.get("rooms")));
    	
    	
    	SearchResultPage searchResults = homePage.clickSearchBtn();
    	boolean destinationDisplayed =
    	        searchResults.isResultDisplayed(data.get("destination"));

    	System.out.println("Destination: " + data.get("destination"));
    	System.out.println("Destination displayed: " + destinationDisplayed);

    	Assert.assertTrue(destinationDisplayed);
    	
    	HotelDetailsPage hotelDetailPage = searchResults.clickHotelTitle(data.get("hotel"));
    	hotelDetailPage.viewRooms();
    	hotelDetailPage.selectRooms(Integer.parseInt(data.get("rooms")));
    	BookingConfirmationPage bookingConfirmation = hotelDetailPage.clickSelect();
    	bookingConfirmation.bookingConfirmationValidation(
    	        data.get("hotel"),
    	        data.get("checkIn"),
    	        data.get("checkOut"),
    	        data.get("adults"),
    	        data.get("children"),
    	        data.get("rooms"),
    	        data.get("language")
    	);
    
    

		bookingConfirmation.enterGuestDetails(data.get("firstName"), data.get("lastName"), data.get("email"), data.get("address"),data.get("city"),data.get("phoneNo"));
	
		bookingConfirmation.selectCountry(data.get("country"));

		bookingConfirmation.selectAddOnServices(
		        Boolean.parseBoolean(data.get("flight")),
		        Boolean.parseBoolean(data.get("carRental")),
		        Boolean.parseBoolean(data.get("taxi"))
		);
	

	}
	

	
	
	@DataProvider
    public Object[][] getData() throws IOException {
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") +"\\src\\test\\java\\resouces\\BookingData.json");
    	 return new Object[][] { 
    		 {data.get(0)}, 
    		 {data.get(1)}};
    	 
    	
	}

}
