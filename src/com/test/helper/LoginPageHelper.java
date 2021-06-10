package com.test.helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.story.config.Config;
import com.story.request.InitialiseDriver;
import com.story.util.GenericFunctions;
import com.story.util.LoggerUtil;
import com.story.util.TestListnerExtentReport;
import com.story.util.LoggerUtil.LogLevel;

public class LoginPageHelper extends PageXpaths{
	
	WebDriver driver;
	GenericFunctions genericfunction;
	PageXpaths getXpath ;
	WebDriverWait wait;
	public LoginPageHelper(WebDriver driver)
	{
		this.driver=driver;
		genericfunction = new GenericFunctions(driver);
		getXpath=new PageXpaths();
		wait = new WebDriverWait(driver, InitialiseDriver.waitTime);
	}

	public void LoginUser(String Username,String Password) throws InterruptedException
	{
		try{	
		
		genericfunction.SendKeys(getXpath.Username_txt, Username);
		LoggerUtil.setlog(LogLevel.INFO, "Entered in username text");
		genericfunction.click((getXpath.Password_txt));
		Thread.sleep(2000);
		
		genericfunction.SendKeysWithoutClear(getXpath.Password_txt, Password);
		LoggerUtil.setlog(LogLevel.INFO, "Entered in password text");

		genericfunction.click(getXpath.LoginSign_btn);
		LoggerUtil.setlog(LogLevel.INFO, "Clicked sign_in button ");
		}
		catch(ElementNotFoundException e)
		{
			System.out.println("here");
			TestListnerExtentReport.setException("Not able to find elements in login page", e.getStackTrace()); 

		}
		
	}
	
	
	
	
}
