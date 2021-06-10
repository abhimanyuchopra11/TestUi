package com.test.request;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentTest;
import com.story.config.Config;
import com.story.servicefactory.DemoServiceFactory;
import com.story.util.LoggerUtil;
import com.story.util.LoggerUtil.LogLevel;
import com.story.util.PropertiesUtil;
import com.story.util.ReportUtil;
import com.story.util.TakeScreenshot;
import com.story.util.TestListnerExtentReport;
import com.story.util.Xls_Reader;

public class InitialiseDriver extends TestListenerAdapter {

	public static int waitTime=300;
	public static int TimeinMillis=5000;
	private  WebDriver Firefoxdriver;
	private  WebDriver Chromedriver;
	public WebDriver driver;
	public static HashMap<String,WebDriver> driversList = new HashMap();
	public DesiredCapabilities capabilities;
	public static String environment="prod";
	public ArrayList<WebDriver> failedDriverList= new ArrayList<WebDriver>();
	

	public WebDriver getDriver()
	{
		return driver;
	}
	public void setFirefoxDriver(String env)
	{
		String Firefoxpath="";
		if(env.equalsIgnoreCase("mac"))
		Firefoxpath = System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("Mac_FirefoxDriverPath");
		
		else if(env.equalsIgnoreCase("win"))
		Firefoxpath = System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("Win_FirefoxDriverPath");

		LoggerUtil.setlog(LogLevel.ONLYLOGS, "Firefox Path -: "+Firefoxpath);
		System.setProperty("webdriver.gecko.driver",Firefoxpath);
		Firefoxdriver = new FirefoxDriver();
		
	}
	public WebDriver getFirefoxDriver()
	{
		return this.Firefoxdriver;
	}
	public void setChromeDriver(String env)
	{
		String Chromepath="";
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-fullscreen");
		
		if(env.equalsIgnoreCase("mac"))
			Chromepath = System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("Mac_ChromeDriverPath");
		
		else if(env.equalsIgnoreCase("win"))
			Chromepath = System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("Win_ChromeDriverPath");

		System.out.println(Chromepath);
		LoggerUtil.setlog(LogLevel.ONLYLOGS, "Chromedriver Path -: "+Chromepath);
		System.setProperty("webdriver.chrome.driver",Chromepath);
		
		Chromedriver = new ChromeDriver(options);
	
	}
	
	
	public WebDriver getChromeDriver()
	{
		return this.Chromedriver;
		
	}
	
	
	@Parameters({"browser","os"})
	@BeforeClass(alwaysRun=true)
	public void InitialiseBrowser(String browser,String os)
	{
		LoggerUtil.setlog(LogLevel.ONLYLOGS, "OS is -: "+os);
		System.out.println("Inside Before class method");
		Reporter.log("********** Browser Initialisesd************** "+ browser, true);
		if(browser.equalsIgnoreCase("firefox"))
		{
			
			setFirefoxDriver(os);
			driver=getFirefoxDriver();
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			
			setChromeDriver(os);
			driver=getChromeDriver();
			
			
			
			
		}
		
//		Reporter.log("Method name for browser "+browser+" is - " + method.getName().trim().toString(),true);
//		driversList.put(method.getName().trim().toString()+","+browser, driver);
		

	}
	
	@Parameters({"browser"})
	@BeforeMethod(alwaysRun=true)
	public void executeBeforeMethod(String browser,Method method)
	{
		Reporter.log("Method name for browser "+browser+" is - " + method.getName().trim().toString(),true);
		driversList.put(method.getName().trim().toString()+","+browser, driver);

	}

	
	@Parameters({"env"})
	@BeforeSuite(alwaysRun = true)
	public  void RunningBeforeSuiteMethod(String env)
	{
		environment=env;
		LoggerUtil.setlog(LogLevel.ONLYLOGS, "Inside BeforeSuite Method");

		try {
			DemoServiceFactory.getConfigInstance();
			PropertiesUtil.loadConstantFile("constant");
			LoggerUtil.setlog(LogLevel.ONLYLOGS, "Loaded constant file");
//			String WaitTime = System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("WaitTime");
//			String TimeInMillsec = System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("TimeinMills");
//			System.out.println(WaitTime);
//			waitTime=Integer.valueOf(WaitTime);
//			TimeinMillis=Integer.valueOf(TimeInMillsec);
//			System.out.println("WAIT time is -: "+waitTime);
		} 
		catch (IOException e1) {
			LoggerUtil.setlog(LogLevel.ONLYLOGS, "Error in Loaded constant file");
			e1.printStackTrace();
		}
		File ScreenshotDir = new File(TakeScreenshot.getScreenshotDirPath());
		File ReportsDir = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("ReportPath"));
		File TestSummaryFile=new File(System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("TestSummaryReport"));
		File schedPdfFile = new File("C:\\Users\\Administrator\\Downloads\\sched_pdf_comparison.pdf");
		File UnequalPdfsDir = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+"Resources/PdfData/NotEqualPdfs");
		try {
			FileUtils.cleanDirectory(ScreenshotDir);
			FileUtils.cleanDirectory(ReportsDir);
			FileUtils.cleanDirectory(TestSummaryFile);
			FileUtils.cleanDirectory(UnequalPdfsDir);
			if(schedPdfFile.delete()) {
				System.out.println(schedPdfFile.getName() + " is deleted!");
			}
			else {
				System.out.println("schedule pdf file delete operation is failed.");
			}
		} catch (IOException e) {
			Reporter.log("Error in deleting Screenshot Folder", true);
			e.printStackTrace();
		} 
		LoggerUtil.setlog(LogLevel.ONLYLOGS, "Deleted Screenshot, Reports, and TestSummary dir ");
		
	}

	
	@AfterClass(alwaysRun=true)
	public void quitdriver()
	{
		Reporter.log("quitting driver",true);
		driver.close();
		driver.quit();
		
	}
	
//	@AfterTest(alwaysRun = true)
//	public void ClearHashMapValue()
//	{
//		 driversList = new HashMap<String, WebDriver>();
//	}
	
	@BeforeTest(alwaysRun = true)
	public void InitializeFailedList()
	{
		System.out.println("On Before Test method");
		driversList = new HashMap<String, WebDriver>();
		TestListnerExtentReport.failedTestMethods=new ArrayList<String>();
		TestListnerExtentReport.extentThreadLocalTemp =  new HashMap<>();
		TestListnerExtentReport.HashMaptemp =  new HashMap<>();
		TestListnerExtentReport.ExceptionHashMaptemp = new HashMap<>();
	}
	
	@AfterSuite(alwaysRun = true)
	public void afterSuite() 
	{
		File tempFile = null;
		try {
			File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("TestSummaryReport"));
			try {
				tempFile = ReportUtil.extractResultTable(Config.getEnvironment(Config.getConfigLocation()), TestListnerExtentReport.fileName);
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		
			ArrayList<HashMap<String, HashMap<String, Integer>>> testReportCompleteName = TestListnerExtentReport.reportStatus;
			if(tempFile!=null)
			{
			System.out.println("temp file is not null");
			
			}
			String reportName = "";
			File f = null;
			for (int i = 0; i < testReportCompleteName.size(); i++) {
				for (String reportNameKey : testReportCompleteName.get(i).keySet()) {
					reportName = reportNameKey;
					f = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("ReportPath"));
					System.out.println(System.getProperty("user.dir")+System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("ReportPath"));
					if (!f.exists()) {
						System.out.println("REPORT FILE NOT CREATED " + reportName);
					}
				}
			}
			if (f.exists()) {
				try {
					FileUtils.copyFileToDirectory(tempFile, file);;
				} catch (IOException e) {
					System.out.println("file not copied");
					e.printStackTrace();
				}
			}

		}
		finally
		{
			tempFile.delete();
		}
		}
	
	
}
