package DarwinBox.DemoAppTest.pages;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelectFlightPage {
	
	private WebDriver driver;
	

	public SelectFlightPage(WebDriver driver) {
		this.driver = driver;
	}
	protected By roundTrip = By.id("RoundTrip");
	protected By onewayTrip = By.id("OneWay");
	
	protected By origninCity = By.name("origin");
	protected By destinationCity = By.name("destination");
	protected By departDate  = By.id("DepartDate");
	protected By returnDate = By.id("ReturnDate");
	
	protected By selectAdults = By.id("Adults");
	protected By selectChildren = By.id("Childrens");
	
	protected By selectInfants = By.id("Infants");
	
	protected By searchButton = By.id("SearchBtn");
	
	protected String dateXpath = "//td[@data-month='currentMonth']//a[text()='currentDate']"; 
	protected By allTicketsFairTo = By.xpath("//div[@data-fromto='HYD_BLR']//ul[@class='listView flights']//li//div");
	protected By allTicketsFairReturn = By.xpath("//div[@data-fromto='BLR_HYD']//ul[@class='listView flights']//li//div");
	protected String ticketByCost = "//div[@data-price='cost']";
	
	protected String flightName = "//div[@data-origin-destination='journey']//div[@class='airlineName']//span";
	protected String timing = "//div[@data-origin-destination='journey']//time//strong";
	protected String duration = "//div[@data-origin-destination='journey']//abbr";
	protected By insuranceBox = By.id("insurance_box");
	protected By bookButton = By.xpath("//form[@action='/flights/initiate-booking']//button[1]");
	protected By continueBookingButton = By.id("itineraryBtn");
	protected By allTicketPrices = By.xpath("//div[@class='listItem ']//th[contains(@class, 'price')]");
	protected By pricesALLTo = By.xpath("//div[@data-fromto='HYD_BLR']//ul[@class='listView flights']//li//div//th[@class='price ']");
	protected By pricesALLReturn = By.xpath("//div[@data-fromto='BLR_HYD']//ul[@class='listView flights']//li//div//th[@class='price ']");
	
	public void checkRounTrip(){
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(roundTrip));
		driver.findElement(roundTrip).click();
	}
		
	public void typeOriginCity(String origin) {
		driver.findElement(origninCity).sendKeys(origin);
	}
	public void typeDestinationCity(String destination) {
		driver.findElement(destinationCity).sendKeys(destination);
	}
	
	public void selectDepartureDate(String month,String date){
		driver.findElement(departDate).click();
		String targetDateXpath = dateXpath.replace("currentMonth", month).replace("currentDate", date);
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(targetDateXpath)));
		driver.findElement(By.xpath(targetDateXpath)).click();
	}
	
	public void selectReturnDate(String month,String date){
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(returnDate));
		driver.findElement(returnDate).click();
		String targetDateXpath = dateXpath.replace("currentMonth", month).replace("currentDate", date);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(targetDateXpath)));
		driver.findElement(By.xpath(targetDateXpath)).click();
	}
	
	public void selectAdults(String noOfAdults){
		Select adults  = new Select(driver.findElement(selectAdults));
		adults.selectByValue(noOfAdults);
		
	}
	public void selectChildren(String noOfChildren){
		Select children  = new Select(driver.findElement(selectChildren));
		children.selectByValue(noOfChildren);
		
	}
	public void selectInfants(String noOfInfants){
		Select infants  = new Select(driver.findElement(selectInfants));
		infants.selectByValue(noOfInfants);
		
	}
	
	public void clickSearchOption(){
		driver.findElement(searchButton).click();
	}
	
	
	
public void selectSecondHighestTicketFlightForJourney1(String typeOfJourney) throws InterruptedException{
		
		By typeOfJourneyTicket = null;
		if(typeOfJourney.equalsIgnoreCase("to")){
			typeOfJourneyTicket = allTicketsFairTo;
		}
		else{
			typeOfJourneyTicket = allTicketsFairReturn;
			
		}
		Thread.sleep(10000);	
		WebDriverWait wait = new WebDriverWait(driver, 500);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(allTicketPrices));
		List<WebElement> prices = driver.findElements(typeOfJourneyTicket);
		selectTicketByItsCost(Integer.toString(returnSecondHighest(prices)));
	}
	
	public int returnSecondHighest(List<WebElement> ticketFairs) {
		
		List<Integer> fairs = new ArrayList<Integer>();
		for(int i=0 ;i < ticketFairs.size() ; i++){
			fairs.add(Integer.parseInt(ticketFairs.get(i).getAttribute("data-price")));
		}
		
		Collections.sort(fairs);
		return fairs.get(fairs.size()-2);
		
	}
	
	public void selectTicketByItsCost(String cost){
		
		driver.findElement(By.xpath(ticketByCost.replace("cost", cost))).click();
	}
	
	public Map<String, String> getToJouneyDetails(String journey){
		
		HashMap<String, String> toJourneyDetails = new HashMap<String, String>();
		
		toJourneyDetails.put("flightName", driver.findElement(By.xpath(flightName.replace("journey", journey))).getText());
		toJourneyDetails.put("timings", driver.findElement(By.xpath(timing.replace("journey", journey))).getText());
		toJourneyDetails.put("duration", driver.findElement(By.xpath(duration.replace("journey", journey))).getText());
		
		return toJourneyDetails;
		
	}
	
public Map<String, String> getToJouneyDetailsInItineraryPage(String journey){
		
		int index = 0;
		if(journey.equalsIgnoreCase("to")){
			index = 2;
		}
		else {
			index = 3;
		}
		Map<String, String> toJourneyDetails = new HashMap<String, String>();
		
		List<WebElement> airline = driver.findElements(By.xpath("//div[@class='airlineName']//span"));
		List<WebElement> timings = driver.findElements(By.xpath("//li[@class='timing col col6']"));
		List<WebElement> duration = driver.findElements(By.xpath("//li[@class='timing col col6']//small"));
		
		String timing = timings.get(index-2).getText().trim();
		timing = timing.substring(0,timing.indexOf(" "));
		
		String journeyDuration = duration.get(index-2).getText().trim();
		journeyDuration = journeyDuration.substring(0,journeyDuration.indexOf(","));
		
		toJourneyDetails.put("flightName", airline.get(index).getText().trim());
		toJourneyDetails.put("timings", timing);
		toJourneyDetails.put("duration",journeyDuration);
		
		return toJourneyDetails;
		
	}
	
	public void clickBookButton(){
		
		driver.findElement(By.id("userAccountLink")).click();
		List<WebElement> bookButtons = driver.findElements((bookButton));
		bookButtons.get(bookButtons.size()-1).click();
		
	}
	
	public void clickOnContinueBookingButton(){
		driver.findElement(continueBookingButton).click();
	}
	
	public void clickInsuranceCheckBox(){
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.visibilityOfElementLocated(insuranceBox));
		driver.findElement(insuranceBox).click();
	}
	
	
	public static void main(String args[]){
		String timing = "15:50 — 16:55";
		timing = timing.substring(0,timing.indexOf(" "));
		System.out.println(timing);
				
	}
}



