package com.test.testing;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.story.helper.CategoryPageHelper;
import com.story.helper.CreateProjectHelper;
import com.story.helper.LoginPageHelper;
import com.story.helper.PageXpaths;
import com.story.helper.ReportPageHelper;
import com.story.helper.SchedulePageHelper;
import com.story.helper.ScriptPageHelper;
import com.story.request.InitialiseDriver;
import com.story.servicefactory.DemoServiceFactory;
import com.story.util.GenericFunctions;
import com.story.util.LoggerUtil;
import com.story.util.TestListnerExtentReport;
import com.story.util.Xls_Reader;
import com.story.util.LoggerUtil.LogLevel;

public class FunctionalityTest extends InitialiseDriver {
	static int count = 0;
	int failcount = 0;
	public WebDriver driver;
	GenericFunctions genericfunction;
	Xls_Reader reader;
	public static String CurrentBrowser;

	static String Url;
	static String Password;
	static String Username;

	PageXpaths getXpath = new PageXpaths();
	boolean flag = false;
	WebDriverWait wait;

	@BeforeClass(alwaysRun=true)
	public void InitializeMethod() {
		System.out.println("Inside Before class method ");
		try {
			Url = DemoServiceFactory.getConfigInstance().getConfig("Endpoint.url");
			System.out.println("url is" + Url);
			Password = DemoServiceFactory.getConfigInstance().getConfig("Password_1");
			Username = DemoServiceFactory.getConfigInstance().getConfig("Username_1");
			reader = new Xls_Reader(DemoServiceFactory.getConfigInstance().getConfig("SheetPath"));
			System.out.println("Running method before run");
		} catch (Exception e) {
			TestListnerExtentReport.setMessage("Not able to load data from config file");
			e.printStackTrace();
		}
			
		driver = getDriver();
		// System.setProperty("webdriver.chrome.driver","/Users/abchopra/Downloads/chromedriver");
		// driver = new ChromeDriver();
		genericfunction = new GenericFunctions(driver);
		genericfunction.SetImplicitWaitInMilliSeconds(6000);
		wait = new WebDriverWait(driver, InitialiseDriver.waitTime);
	}

	@AfterMethod(alwaysRun=true)
	public void deleteCookies() throws InterruptedException 
	{	
	System.out.println("deleting cookies");	
	driver.manage().deleteAllCookies();
	}
	
	
	 
	 @Test(enabled=true,priority = 1,groups={"sanity","regression"})
	public void TestHomeLandingPage() {
		driver.get(Url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(getXpath.ClickSignIn_btn));
		if (genericfunction.isVisible(getXpath.ClickSignIn_btn))
			flag = true;
		if (flag == false)
			LoggerUtil.setlog(LogLevel.FAIL, "failed browser test case");
		Assert.assertEquals(flag, true, "Sign_in button on landing page not found");

	}

	
	 @Test(enabled=true,priority = 2,groups={"sanity","regression"})
	public void TestLogin() throws InterruptedException {
		 flag = false;
		LoginPageHelper loginPage = new LoginPageHelper(driver);
		driver.get(Url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(getXpath.ClickSignIn_btn));
		
		genericfunction.click(getXpath.ClickSignIn_btn);
		LoggerUtil.setlog(LogLevel.INFO, "clicked sign in button home page");
		
		loginPage.LoginUser(Username, Password);

		boolean status = loginPage.CheckLoggedInStatus();
		String title = driver.getTitle();
		

	}

	 @Parameters({ "browser" })
	 @Test(enabled=true,priority = 3,groups={"sanity","regression"})
	public void CreateCategory(String browser) throws InterruptedException {
		 flag = false;
		LoginPageHelper loginPage = new LoginPageHelper(driver);
		

	

}
