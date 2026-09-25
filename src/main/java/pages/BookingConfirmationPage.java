
package pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import base.WebUtilities;

public class BookingConfirmationPage extends WebUtilities {

    WebDriver driver;

    public BookingConfirmationPage(WebDriver driver) {

        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    private By headingLocator = By.tagName("h1");

    private By checkInDateLocator =
            By.xpath("//div[@data-testid='booking-details-date-summary']//time[1]");

    private By checkOutDateLocator =
            By.xpath("//div[@data-testid='booking-details-date-summary']//time[2]");

    private By guestCountLocator =
            By.xpath("//div[@data-capla-component-boundary='b-checkout-bp-accommodation/BookingDetails']"
                    + "//div[@data-testid='booking-details-date-summary']"
                    + "/following-sibling::div[2]"
                    + "//div/div[2]");

    @FindBy(css = "input[data-testid='user-details-firstname']")
    WebElement firstName;

    @FindBy(css = "input[data-testid='user-details-lastname']")
    WebElement lastName;

    @FindBy(css = "input[data-testid='user-details-email']")
    WebElement userEmail;

    @FindBy(css = "input[data-testid='user-details-address1']")
    WebElement userAddress;

    @FindBy(css = "input[data-testid='user-details-city']")
    WebElement userCity;

    @FindBy(css = "input[data-testid='phone-number-input']")
    WebElement userPhone;

    @FindBy(css = "input[data-testid='user-details-sign-in-to-save']")
    WebElement signIn;

    @FindBy(css = "select[data-testid='user-details-cc1']")
    WebElement selectedCountry;

    @FindBy(id = "interested_flight")
    WebElement flightAddOn;

    @FindBy(id = "interested_car_rentals")
    WebElement carRentalsAddOn;

    @FindBy(id = "interested_taxi")
    WebElement taxiAddOn;

    @FindBy(xpath = "//button[@data-popover-content-id='bp-submit-popover']")
    WebElement submit;



    public boolean verifyHotelName(String hotelName) {

        WebElement heading =
                waitForElementToAppear(headingLocator);

        return heading.getText().trim().contains(hotelName);
    }





    public boolean verifyCheckInDate(
            String date,
            String language) {

        WebElement checkIn =
                waitForElementToAppear(checkInDateLocator);

        String actualText =
                checkIn.getText().trim();

        Locale locale =
                getLocale(language);

        try {

            String actualDateLine =
                    actualText.split("\\n")[1].trim();

            // Remove day of week
            String[] dateParts =
                    actualDateLine.split("\\s+", 2);

            String dateOnly =
                    dateParts[1];

            DateTimeFormatter actualFormatter;

            if ("English (US)".equalsIgnoreCase(language)) {

                actualFormatter =
                        DateTimeFormatter.ofPattern(
                                "MMM d, yyyy",
                                locale);

            } else {

                actualFormatter =
                        DateTimeFormatter.ofPattern(
                                "d MMM yyyy",
                                locale);
            }

            LocalDate actualDate =
                    LocalDate.parse(
                            dateOnly,
                            actualFormatter);

            LocalDate expectedDate =
                    LocalDate.parse(
                            date,
                            DateTimeFormatter.ofPattern(
                                    "yyyy-MM-dd"));

            System.out.println(
                    "Actual check-in: " + actualDate);

            System.out.println(
                    "Expected check-in: " + expectedDate);

            return actualDate.equals(expectedDate);

        } catch (Exception e) {

            System.out.println(
                    "Unable to parse check-in date: "
                            + actualText);

            e.printStackTrace();

            return false;
        }
    }


    

    public boolean verifyCheckOutDate(
            String date,
            String language) {

        WebElement checkOut =
                waitForElementToAppear(
                        checkOutDateLocator);

        String actualText =
                checkOut.getText().trim();

        Locale locale =
                getLocale(language);

        try {

            String actualDateLine =
                    actualText.split("\\n")[1].trim();

            // Remove day of week
            String[] dateParts =
                    actualDateLine.split("\\s+", 2);

            String dateOnly =
                    dateParts[1];

            DateTimeFormatter actualFormatter;

            if ("English (US)".equalsIgnoreCase(language)) {

                actualFormatter =
                        DateTimeFormatter.ofPattern(
                                "MMM d, yyyy",
                                locale);

            } else {

                actualFormatter =
                        DateTimeFormatter.ofPattern(
                                "d MMM yyyy",
                                locale);
            }

            LocalDate actualDate =
                    LocalDate.parse(
                            dateOnly,
                            actualFormatter);

            LocalDate expectedDate =
                    LocalDate.parse(
                            date,
                            DateTimeFormatter.ofPattern(
                                    "yyyy-MM-dd"));

            System.out.println(
                    "Actual check-out: " + actualDate);

            System.out.println(
                    "Expected check-out: " + expectedDate);

            return actualDate.equals(expectedDate);

        } catch (Exception e) {

            System.out.println(
                    "Unable to parse check-out date: "
                            + actualText);

            e.printStackTrace();

            return false;
        }
    }




    public boolean verifyGuestCount(
            String adults,
            String children,
            String rooms,
            String language) {

        WebElement guestCount =
                waitForElementToAppear(
                        guestCountLocator);

        String actualGuests =
                guestCount.getText();

        String adultText =
                getAdultText(language);

        String childText =
                getChildText(language);

        String roomText =
                getRoomText(language);

        String expectedAdults =
                adults + " " + adultText;

        String expectedChildren =
                children + " " + childText;

        String expectedRooms =
                rooms + " " + roomText;

        System.out.println(
                "Actual Guests: " + actualGuests);

        System.out.println(
                "Expected Adults: " + expectedAdults);

        System.out.println(
                "Expected Children: " + expectedChildren);

        System.out.println(
                "Expected Rooms: " + expectedRooms);

        return actualGuests.contains(expectedAdults)
                && actualGuests.contains(expectedChildren)
                && actualGuests.contains(expectedRooms);
    }



    public void bookingConfirmationValidation(
            String hotel,
            String checkIn,
            String checkOut,
            String adults,
            String children,
            String rooms,
            String language) {

        boolean hotelValid =
                verifyHotelName(hotel);

        System.out.println(
                "Hotel validation: " + hotelValid);

        boolean checkInValid =
                verifyCheckInDate(
                        checkIn,
                        language);

        System.out.println(
                "Check-in validation: "
                        + checkInValid);

        boolean checkOutValid =
                verifyCheckOutDate(
                        checkOut,
                        language);

        System.out.println(
                "Check-out validation: "
                        + checkOutValid);

        boolean guestsValid =
                verifyGuestCount(
                        adults,
                        children,
                        rooms,
                        language);

        System.out.println(
                "Guests validation: "
                        + guestsValid);

        Assert.assertTrue(hotelValid);
        Assert.assertTrue(checkInValid);
        Assert.assertTrue(checkOutValid);
        Assert.assertTrue(guestsValid);
    }



    public void enterGuestDetails(
            String fName,
            String lName,
            String email,
            String address,
            String city,
            String phoneNo) {

        System.out.println(
                "URL after reserve: "
                        + driver.getCurrentUrl());

        System.out.println(
                "Title after reserve: "
                        + driver.getTitle());

        waitForUrlContains("/book.html");

        System.out.println(
                "Booking details page loaded.");

        System.out.println(
                "Title after waiting: "
                        + driver.getTitle());

        waitForElementToAppear(
                By.cssSelector(
                        "input[data-testid='user-details-firstname']"));

        System.out.println(
                "About to enter first name");

        firstName.sendKeys(fName);

        System.out.println(
                "First name entered");

        System.out.println(
                "About to enter last name");

        lastName.sendKeys(lName);

        System.out.println(
                "Last name entered");

        System.out.println(
                "About to enter email");

        userEmail.sendKeys(email);

        System.out.println(
                "Email entered");

        System.out.println(
                "About to enter address");

        userAddress.sendKeys(address);

        System.out.println(
                "Address entered");

        System.out.println(
                "About to enter city");

        userCity.sendKeys(city);

        System.out.println(
                "City entered");

        System.out.println(
                "About to enter phone");

        userPhone.sendKeys(phoneNo);

        System.out.println(
                "Phone entered");
    }



    public void selectCountry(
            String countryCode) {

        String countryName;

        switch (countryCode.toUpperCase()) {

            case "IN":
                countryName = "India";
                break;

            case "MY":
                countryName = "Malaysia";
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported country code: "
                                + countryCode);
        }

        Select select =
                new Select(selectedCountry);

        select.selectByVisibleText(
                countryName);
    }




    public void selectAddOnServices(
            boolean flight,
            boolean carRental,
            boolean taxi) {

        if (flight) {

            System.out.println(
                    "Clicking Flight Add-on");

            WebElement flightElement =
                    waitForElementToAppear(
                            By.id("interested_flight"));

            flightElement.click();

            System.out.println(
                    "Flight Add-on clicked");
        }

        if (carRental) {

            System.out.println(
                    "Clicking Car Rental Add-on");

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block: 'center'});",
                            carRentalsAddOn);

            carRentalsAddOn.click();

            System.out.println(
                    "Car Rental Add-on clicked");
        }

        if (taxi) {

            System.out.println(
                    "Clicking Taxi Add-on");

            WebElement taxiElement =
                    waitForElementToAppear(
                            By.id("interested_taxi"));

            taxiElement.click();

            System.out.println(
                    "Taxi Add-on clicked");
        }
    }




    public boolean isValidationErrorDisplayed(
            WebElement field) {

        String errorId =
                field.getAttribute(
                        "aria-describedby");

        System.out.println(
                "Field: "
                        + field.getAttribute(
                                "data-testid")
                        + " | value: "
                        + field.getAttribute("value")
                        + " | aria-invalid: "
                        + field.getAttribute("aria-invalid")
                        + " | aria-describedby: "
                        + errorId);

        if (errorId == null
                || errorId.isEmpty()) {

            return false;
        }

        String[] ids =
                errorId.split("\\s+");

        for (String id : ids) {

            try {

                if (driver.findElement(
                        By.id(id)).isDisplayed()) {

                    return true;
                }

            } catch (NoSuchElementException e) {

                // Continue checking other IDs
            }
        }

        return false;
    }



    public boolean isPhoneValidationErrorDisplayed() {

        String phoneId =
                userPhone.getAttribute("id");

        String errorId =
                phoneId + "-note";

        try {

            WebElement error =
                    waitForElementToAppear(
                            By.id(errorId));

            return error.isDisplayed();

        } catch (NoSuchElementException e) {

            return false;
        }
    }


    public void validateMandatoryFieldErrors() {

        Assert.assertTrue(
                isValidationErrorDisplayed(firstName),
                "First Name validation error is not displayed");

        Assert.assertTrue(
                isValidationErrorDisplayed(lastName),
                "Last Name validation error is not displayed");

        Assert.assertTrue(
                isValidationErrorDisplayed(userEmail),
                "Email validation error is not displayed");

        Assert.assertTrue(
                isValidationErrorDisplayed(userAddress),
                "Address validation error is not displayed");

        Assert.assertTrue(
                isValidationErrorDisplayed(userCity),
                "City validation error is not displayed");

        Assert.assertTrue(
                isPhoneValidationErrorDisplayed(),
                "Phone validation error is not displayed");
    }



    public void validateInvalidEmail() {

        Assert.assertTrue(
                isValidationErrorDisplayed(userEmail));
    }


 

    public void validateInvalidPhoneNo() {

        Assert.assertTrue(
                isPhoneValidationErrorDisplayed(),
                "Phone validation error is not displayed");
    }



    public void validateInvalidDataTypeErrors() {

        Assert.assertTrue(
                isValidationErrorDisplayed(firstName),
                "First Name validation error is not displayed");

        Assert.assertTrue(
                isValidationErrorDisplayed(lastName),
                "Last Name validation error is not displayed");

        Assert.assertTrue(
                isValidationErrorDisplayed(userEmail),
                "Email validation error is not displayed");

        Assert.assertTrue(
                isValidationErrorDisplayed(userAddress),
                "Address validation error is not displayed");

        Assert.assertTrue(
                isValidationErrorDisplayed(userCity),
                "City validation error is not displayed");

        Assert.assertTrue(
                isPhoneValidationErrorDisplayed(),
                "Phone validation error is not displayed");
    }




    public void clickNext() {

        waitForElementToAppear(
                By.xpath(
                        "//button[@data-popover-content-id='bp-submit-popover']"));

        submit.click();
    }
}



