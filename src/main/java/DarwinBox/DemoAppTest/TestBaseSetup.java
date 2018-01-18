package DarwinBox.DemoAppTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public class TestBaseSetup {

	private static WebDriver driver;
	private static String driverPath = System.getProperty("user.dir") + File.separator
			+ "libs" + File.separator;
	private static String screenshotspath = System.getProperty("user.dir") + File.separator
			+"screenshots";


	public static WebDriver getDriver() {
		return driver;
	}
	
	@BeforeSuite
	
	public void cleanScreenshotPath() throws IOException{
		FileUtils.cleanDirectory(new File(screenshotspath)); 
	}

	private void setDriver(String browserType, String appURL) {
		switch (browserType) {
		case "chrome":
			driver = initChromeDriver(appURL);
			break;
		case "firefox":
			driver = initFirefoxDriver(appURL);
			break;
			
		case "ie":
			driver = initInternetDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType
					+ " is invalid, Launching Firefox as browser of choice..");
			driver = initFirefoxDriver(appURL);
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private static WebDriver initChromeDriver(String appURL) {
		
		System.setProperty("webdriver.chrome.driver", driverPath
				+ "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}
	private static WebDriver initInternetDriver(String appURL) {
		
		System.setProperty("webdriver.chrome.driver", driverPath
				+ "IEDriverServer3.4.0.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}

	
	@Parameters({ "browserType", "appURL" })
	@BeforeClass
	public void initializeTestBaseSetup(String browserType, String appURL) {
		try {
			setDriver(browserType, appURL);

		} catch (Exception e) {
			System.out.println("Error....." + e.getStackTrace());
		}
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	
	
	/*public static void main (String args[]) {
		String driverPath = System.getProperty("user.dir") + File.separator
				+ "libs" + File.separator;
		// TODO Auto-generated method stub
		System.out.println(driverPath);
	}*/
}
