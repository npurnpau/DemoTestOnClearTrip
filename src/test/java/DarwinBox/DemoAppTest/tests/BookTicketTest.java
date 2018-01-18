package DarwinBox.DemoAppTest.tests;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import DarwinBox.DemoAppTest.TestBaseSetup;
import DarwinBox.DemoAppTest.pages.SelectFlightPage;



/**
 * Unit test for simple App.
 */
public class BookTicketTest extends TestBaseSetup{
	private WebDriver driver;
	String originCity = "Hyderabad, IN - Rajiv Gandhi International (HYD)";
	String destinationCity = "Bangalore, IN - Kempegowda International Airport (BLR)";
	String toJourney = "HYD_BLR";
    String returnJourney = "BLR_HYD";
    
	@BeforeClass
	public void setUp() {
		driver=getDriver();
	}
    @Test
    public void searchForAFlight() throws InterruptedException {    	    	
    	SelectFlightPage  selectFlightPage = new SelectFlightPage(driver);
    	// Select the details to book tickets 
    	selectFlightPage.typeOriginCity(originCity);
    	selectFlightPage.typeDestinationCity(destinationCity);
    	selectFlightPage.checkRounTrip();
    	selectFlightPage.selectDepartureDate("9", "30");
    	selectFlightPage.selectReturnDate("10","5");
    	selectFlightPage.selectAdults("2");
    	selectFlightPage.selectChildren("2");
    	selectFlightPage.clickSearchOption();
    	
    	// Select tickets for To and Return journeys with second highest price
    	selectFlightPage.selectSecondHighestTicketFlightForJourney1("to");
    	selectFlightPage.selectSecondHighestTicketFlightForJourney1("from");
    	selectFlightPage.clickBookButton();
    	selectFlightPage.clickInsuranceCheckBox();
    	
    	// Save the selected ticket details 
    	Map<String, String> toJoureyDeatils  = selectFlightPage.getToJouneyDetails(toJourney);
    	Map<String, String> returnJourneyDetails  = selectFlightPage.getToJouneyDetails(returnJourney);
    	selectFlightPage.clickOnContinueBookingButton();
    	
    	// Retrieve the details about tickets that were booked 
    	Map<String, String> toJourneyItineryDetails  = selectFlightPage.getToJouneyDetailsInItineraryPage("to");
    	Map<String, String> returnJourneyItineryDetails = selectFlightPage.getToJouneyDetailsInItineraryPage("from");
    	
    	// Validate the Details Booked vs Actual Tickets for To journey
    	Assert.assertEquals(toJoureyDeatils.get("flightName"),toJourneyItineryDetails.get("flightName"));
    	Assert.assertEquals(toJoureyDeatils.get("timings"),toJourneyItineryDetails.get("timings"));
    	Assert.assertEquals(toJoureyDeatils.get("duration"),toJourneyItineryDetails.get("duration"));
    	
    	//Validate the Details Booked vs Actual Tickets for return journey
    	Assert.assertEquals(returnJourneyDetails.get("flightName"),returnJourneyItineryDetails.get("flightName"));
    	Assert.assertEquals(returnJourneyDetails.get("timings"),returnJourneyItineryDetails.get("timings"));
    	Assert.assertEquals(returnJourneyDetails.get("duration"),returnJourneyItineryDetails.get("duration"));
    	
    }
    
    @AfterClass
    public void tearDown(){
    	driver.quit();
    }
    
}
