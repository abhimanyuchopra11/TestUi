package com.test.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TakeScreenshot {

	public  static WebDriver driver;	
	
	 public void takeScreenShot(String methodName,WebDriver driver) {
		 
	    	 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	            try {
					FileUtils.copyFile(scrFile, new File(getScreenshotDirPath()+methodName+".png"));
					System.out.println("***Placed screen shot in "+getScreenshotDirPath()+" ********");
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }
	 
	 public static String getScreenshotDirPath()
	    {
	    	String path = System.getProperty("user.dir");
	    	String scrnshotfile=path+"/ScreenShots/";
	    	return scrnshotfile;
	    }
	
	
}
