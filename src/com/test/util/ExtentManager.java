package com.test.util;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	private ExtentManager(){

	}

	private static ExtentReports extentReport;

	public static synchronized ExtentReports getInstance(String reportCompleteName) {
System.out.println(System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("ReportPath")+System.getProperty("file.separator")+reportCompleteName);

//		if (extentReport == null) {
extentReport = new ExtentReports(System.getProperty("user.dir")+System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("ReportPath")+System.getProperty("file.separator")+reportCompleteName, true);
//		}
		return extentReport;
	}
	
	public static synchronized void removeInstance() {
		extentReport = null;
		
	}
	
}