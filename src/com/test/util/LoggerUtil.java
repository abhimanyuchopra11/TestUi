package com.test.util;

import com.relevantcodes.extentreports.LogStatus;

public class LoggerUtil {

	public enum LogLevel {
	    PASS, 
	    FAIL, 
	    FATAL, 
	    ERROR, 
	    WARNING, 
	    INFO, 
	    SKIP,
	    UNKNOWN,
	    ONLYLOGS
	}
	
	public static void setlog(LogLevel level, String message){
		// For sysout
		System.out.println(message);
		//For Report
		if(level != LogLevel.ONLYLOGS)
			TestListnerExtentReport.setLog( LogStatus.valueOf(level.toString()),message);
	}
	
	public static String setException(String message, StackTraceElement[] stackTrace) {
		
		//For Report
		TestListnerExtentReport.setException(message, stackTrace);
		//for sysout
		for(StackTraceElement stackTraceElement : stackTrace) {                         
			message = message + System.lineSeparator() + stackTraceElement;
		}
		
		return message;
	}
}
