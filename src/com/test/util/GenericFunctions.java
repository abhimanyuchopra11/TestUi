
package com.test.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.ehcache.CacheManager;
import org.ehcache.core.Ehcache;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.test.request.InitialiseDriver;
import com.test.util.LoggerUtil.LogLevel;
import java.awt.Robot;
import com.testautomationguru.utility.CompareMode;
import com.testautomationguru.utility.PDFUtil;

public class GenericFunctions {
	WebDriver driver;
	WebDriverWait wait;

	public GenericFunctions(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, InitialiseDriver.waitTime);

	}

	public void click(By Xpath) {

		try {
			Thread.sleep(1000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Xpath));
			wait.until(ExpectedConditions.elementToBeClickable(Xpath));
			if (driver.findElement(Xpath).isDisplayed() && driver.findElement(Xpath).isEnabled())
				System.out.println("Element to be clicked is  displayed and enabled");
			else
				System.out.println("elemet to be clicked is not displayed");
			driver.findElement(Xpath).click();
		} catch (Exception e) {
			
		   e.printStackTrace();
			LoggerUtil.setlog(LogLevel.INFO, "Element to be clicked not found");
			TestListnerExtentReport.setException(e.getMessage(), e.getStackTrace());
		}

	}

	public void SendKeys(By Xpath, String text) {
			try {
				Thread.sleep(2000);
				wait.until(ExpectedConditions.visibilityOfElementLocated(Xpath));
				driver.findElement(Xpath).clear();
				driver.findElement(Xpath).sendKeys(text);
			} 
			catch (Exception e) {
			 e.printStackTrace();
			TestListnerExtentReport.setException("Not able to send keys on given element", e.getStackTrace());
		}
	}
	
	public void SendKeysWithoutClear(By Xpath, String text) 
	{
		try {
			Thread.sleep(2000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(Xpath));
			driver.findElement(Xpath).sendKeys(text);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			TestListnerExtentReport.setException("Not able to send keys on given element", e.getStackTrace());
			e.printStackTrace();
		}
	}

	public String GenerateRandomString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String date = sdf.format(cal.getTime());
		date = date.replace(":", "").substring(0, 6);
		return date;
	}

	public void wait(WebDriver driver) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(2000, TimeUnit.MILLISECONDS);
		wait.withTimeout(240, TimeUnit.SECONDS);

	}

	public Boolean isVisible(By elemetXpath) {
		try{
		wait.until(ExpectedConditions.visibilityOfElementLocated(elemetXpath));
		if (driver.findElements(elemetXpath).size() != 0) {
			if (driver.findElement((elemetXpath)).isDisplayed())
				return true;
			else
				return false;
		} else
			return false;
		}
		catch(Exception e)
		{
			 e.printStackTrace();
			System.out.println(e.getMessage());
			LoggerUtil.setlog(LogLevel.INFO, "Element not visible Exception");
			TestListnerExtentReport.setException(e.getMessage(), e.getStackTrace());
			return false;
		}
	}

	public String SetImplicitWaitInMilliSeconds(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.MILLISECONDS);
		return "Timeout set to " + timeOut + " milli seconds.";
	}

	public void ClickElement_ByIndex(By xpath , int index)
	{
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
			List<WebElement> Elements = driver.findElements(xpath);
			Elements.get(index).click();
		} catch (Exception e) {
			
			 e.printStackTrace();
			System.out.println("element not found");
			LoggerUtil.setlog(LogLevel.INFO, "Element not found Exception");
			TestListnerExtentReport.setException(e.getMessage(), e.getStackTrace());
			
		}	
	}
	
	public void ClickElementByName(By xpath, String Name)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
		click(xpath);
	}
	

	public String getElementName(By xpath,int index)
	{
		wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
		List<WebElement> Elements = driver.findElements(xpath);
		String text = Elements.get(index).getText();
		return text;
		
	}

	public String getElementAttribute(By xpath,int index,String Attribute)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
		List<WebElement> Elements = driver.findElements(xpath);
		String text = Elements.get(index).getAttribute(Attribute);
		return text;
		
	}
	
	public WebElement GetElements_ByIndex(int index,By xpath)
	{
		List<WebElement> Elements = driver.findElements(xpath);	
		return Elements.get(index);
		
	}
	
	public boolean NotVisible(By xpath)
	{
		try{
		Thread.sleep(3000);	
		if(driver.findElement(xpath).isDisplayed())
		{
		System.out.println("deleted element is displayed");	
			return false;
		}
		else
			return true;
		}
		catch(Exception e)
		{
			System.out.println("In catch - deleted element is not displayed");	
			return true;
		}
		
	}
	
	public static boolean comparePdfs(String ActualPdf,String CurrentPdf) throws IOException
    {
        PDFUtil pdfUtil = new PDFUtil();
        int pagecountCurrent = pdfUtil.getPageCount(CurrentPdf); 
        int pagecountActual = pdfUtil.getPageCount(ActualPdf);
        if(pagecountCurrent!=pagecountActual)
            return false;
       
        pdfUtil.highlightPdfDifference(true);
        pdfUtil.setImageDestinationPath(System.getProperty("user.dir") + System.getProperty("file.separator")+"Resources/PdfData/NotEqualPdfs");
        pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
        boolean comparedbol = pdfUtil.compare(ActualPdf, CurrentPdf);
        return comparedbol;
    }
	
}
