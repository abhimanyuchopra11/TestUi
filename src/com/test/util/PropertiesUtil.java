package com.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.test.config.Config;


public class PropertiesUtil {

	private static Properties constProperties;
	private static Properties ReportProperties;
	private static Properties ScheduleProperties;
	private static Properties load(String fileName) throws IOException {
		Properties prop = new Properties();
		InputStream is = null;
		
			fileName = fileName + ".properties";
			System.out.println("constant properties path -: "+System.getProperty("user.dir")+"/Resources" );
			prop.load(new FileInputStream(System.getProperty("user.dir")+"/Resources/"+fileName));
		System.out.println("File Loaded successfully :: " + fileName);
		return prop;

	}

	public static synchronized void loadConstantFile(String fileName) throws IOException {
		if (constProperties == null)
			constProperties = load(fileName);
		else
			System.out.println("Already Loaded File :: " + fileName);
	}

	public static String getConstantProperty(String property) {
		if (constProperties == null)
			return null;

		return constProperties.getProperty(property).trim();
	}
	
	public static synchronized void LoadReportTestData(String fileName) throws IOException 
	{
		
		if (ReportProperties == null)
			ReportProperties = load(fileName);
		else
			System.out.println("Already Loaded File :: " + fileName);
	}
	
	public static String getReportProperty(String property)
	{
		if(ReportProperties==null)
			return null;
		
		return ReportProperties.getProperty(property).trim();
	}
	
	public static synchronized void LoadScheduleTestData(String fileName) throws IOException 
	{
		
		if (ScheduleProperties == null)
			ScheduleProperties = load(fileName);
		else
			System.out.println("Already Loaded File :: " + fileName);
		
		System.out.println("Schedule TestData : " + ScheduleProperties.getProperty("schedule_Test_Data_UKTV1"));
	}
	
	public static String getScheduleProperty(String property)
	{
		if(ScheduleProperties==null)
			return null;
		
		return ScheduleProperties.getProperty(property).trim();
	}
	
public static void main(String[] args) throws IOException {
	
	loadConstantFile("constant");
	
}
	
}
