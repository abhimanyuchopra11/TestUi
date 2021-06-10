package com.story.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;

import com.story.request.InitialiseDriver;


public class TestListner implements ITestListener {
	WebDriver driver=null;
	
    @Override
	    public void onTestFailure(ITestResult result) {
    	TakeScreenshot tc = new TakeScreenshot();
    	String methodName = result.getMethod().getMethodName().trim().toString();
    	Reporter.log("Error -: "+methodName+" method has failed from test class "+result.getTestClass().getName(),true);
		System.out.println("hash map size "+InitialiseDriver.driversList.size());
		for(Map.Entry<String, WebDriver> getDriver:InitialiseDriver.driversList.entrySet())
		{
			String[] value=getDriver.getKey().split(",");
			String keyvalue=value[0];
			String browserName=value[1];
			System.out.println(" browsername in failure method " + browserName);
			if(methodName.equals(keyvalue))
			{
				Reporter.log("Title of failed test driver - : "+getDriver.getValue().getTitle(),true);
			tc.takeScreenShot(methodName+"_"+browserName,getDriver.getValue());
			break;
			}
		}	
	    }

		@Override
		public void onStart(ITestContext arg0) {
	    	
			
		}

		@Override
		public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void onTestSkipped(ITestResult result) {
	    	Reporter.log("******** "+result.getMethod().getMethodName()+" method has skipped its execution ",true);
			
		}

		@Override
		public void onTestStart(ITestResult result) {
	    	Reporter.log("******** "+result.getMethod().getMethodName()+" method has started  execution ",true);
			
		}

		@Override
		public void onTestSuccess(ITestResult result) {
	    	Reporter.log("******** "+result.getMethod().getMethodName()+" method has completed its execution successfully ",true);
			
		}
		
		@Override
		public void onFinish(ITestContext arg0) {
		
		}
	

	
		
}
