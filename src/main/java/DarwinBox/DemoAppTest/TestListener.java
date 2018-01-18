package DarwinBox.DemoAppTest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener{
	private WebDriver driver;
	private static String screenshotspath = System.getProperty("user.dir") + File.separator
			+"screenshots";

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	public void onTestFailure(ITestResult result) {
		
		
		// TODO Auto-generated method stub
		System.out.println("***** Error "+result.getName()+" test has failed *****");
    	String methodName=result.getName().toString().trim();
    	takeScreenShot(methodName);
		
	}

	
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
    public void takeScreenShot(String methodName) {
    	//get the driver
    	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    	driver=TestBaseSetup.getDriver();
    	 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
         //The below method will save the screen shot in d drive with test method name 
            try {
				FileUtils.copyFile(scrFile, new File(screenshotspath+File.separator+methodName+timeStamp+".png"));
				System.out.println("***Placed screen shot in "+screenshotspath+" ***");
			} catch (IOException e) {
				e.printStackTrace();
			}
    }
       

}
