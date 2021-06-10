package com.test.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IResultMap;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.Test;

public class TestListnerExtentReport implements ITestListener,ISuiteListener,IInvokedMethodListener, IRetryAnalyzer,IAnnotationTransformer {

	private static String exception="" ;
	private  ExtentReports extentReport = null;
	//	private static ExtentTest test = null;

	public static ArrayList<String> testReportCompleteName = null;
	public static ArrayList<HashMap <String, HashMap <String, Integer>>> reportStatus = new ArrayList<HashMap <String, HashMap <String, Integer>>>();
	
	HashMap<String, Integer> status=new HashMap<String, Integer>();
	
	String testReportName = "";
	
	public static Test testName = null;
	public static String fileName = null;
	public static String startTime = null;
	public static String endTime = null;
	
	public static String subject = null;
	
	private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<ExtentTest>();
	
	public static HashMap<String,HashMap<String,ExtentTest>> extentThreadLocalTemp ;
	public static HashMap<String,ExtentTest> HashMaptemp ;
	public static HashMap<String,String> ExceptionHashMaptemp ;
	
	
	private String testNameFromXml = null;
	
	public static ArrayList<String> failedTestMethods;
	boolean flag= false;
	//-----------------------------------------------------------------------------------------

	 public int retryCount = 0;
	    private int maxRetryCount = 1;
	    
	   
	// Below method returns 'true' if the test method has to be retried else 'false' 
	//and it takes the 'Result' as parameter of the test method that just ran
	    public boolean retry(ITestResult result) {
	    	//retryCount = 0;
	        if (retryCount < maxRetryCount) {
	            System.out.println("Retrying test " + result.getMethod().getMethodName() + " with status "
	                    + getResultStatusName(result.getStatus()) + " for the " + (retryCount+1) + " time(s).");
	            retryCount++;
	            return true;
	        }
	        retryCount = 0;
	        return false;
	    }
	    
	    public String getResultStatusName(int status) {
	    	String resultName = null;
	    	if(status==1)
	    		resultName = "SUCCESS";
	    	if(status==2)
	    		resultName = "FAILURE";
	    	if(status==3)
	    		resultName = "SKIP";
			return resultName;
	    }
	
	    @Override
		public void transform(ITestAnnotation testannotation, Class testClass,
				Constructor testConstructor, Method testMethod)	{
			IRetryAnalyzer retry = testannotation.getRetryAnalyzer();

			if (retry == null)	{
				testannotation.setRetryAnalyzer(TestListnerExtentReport.class);
			}
		}
	
	
	
	public void onStart(ITestContext context) {

		System.out.println("onStart extent report ");
		
		try {
			File file = new File(System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("ReportPath"));
			System.out.println("file path is - : "+System.getProperty("user.dir") + System.getProperty("file.separator")+PropertiesUtil.getConstantProperty("ReportPath"));
			if (!file.exists()){
				file.mkdir();
			}
			testReportCompleteName = new ArrayList<String>();
			
			status.put("pass",0);
			status.put("fail",0);
			status.put("skip",0);
			
			DateFormat df = new SimpleDateFormat("dd_MM_yy_HHmmss");
			Date dateobj = new Date();
			String reportName =context.getName()+"_" + df.format(dateobj) ;
			fileName = context.getSuite().getName()+ System.getProperty("file.separator")+context.getName();
			
			subject = context.getSuite().getName()+ " Report for "+context.getName();
			
			String currentReport = reportName + ".html";
			testReportName =  currentReport;
			testReportCompleteName.add(testReportName); 
			System.out.println("testReportCompleteName="+ testReportCompleteName);
			extentReport = ExtentManager.getInstance(testReportCompleteName.get(testReportCompleteName.size() - 1));
			extentReport.config().reportName("Report :: ");
			extentReport.config().reportHeadline(PropertiesUtil.getConstantProperty("ReportHeadline"));
			testNameFromXml = context.getName();
			System.out.println("testNameFromXml "+testNameFromXml); 
			extentTestThreadLocal.set(extentReport.startTest("Pre Condition ", "Test Run on " + testNameFromXml));
			extentTestThreadLocal.get().log(LogStatus.INFO, " INFO");
			testName = extentTestThreadLocal.get().getTest();
		
			System.out.println("test name is " + testName);
//			suiteName = context.getSuite().getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestStart(ITestResult result) {
		try {
			flag=false;
			
//			if(extentTestThreadLocal==null)
//			{
//				System.out.println("extent thread loclal is null");
//			}
//			System.out.println("Started Test: " + result.getName());
			
			//System.out.println("test started "+extentTestThreadLocal.get().getTest());
			
			//		test = extentReport.startTest(result.getName(), "Test Run on " + testNameFromXml);
//			if (testName == extentTestThreadLocal.get().getTest()){
//				extentReport.endTest(extentTestThreadLocal.get());
//			}
			
			if(!failedTestMethods.contains(result.getMethod().getMethodName().toString()))
			{
			flag=true;	
			failedTestMethods.add(result.getMethod().getMethodName().toString());
			}
			System.out.println("Rerunning Test case flag value is -: " + flag);
			extentTestThreadLocal.set(extentReport.startTest(result.getMethod().getMethodName(), "Test Run on " + testNameFromXml));
			extentTestThreadLocal.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Finished Test: " + result.getName() + " :PASSED");
		if(flag==false)
		{
			ExceptionHashMaptemp.remove(result.getMethod().getMethodName().toString());
			extentThreadLocalTemp.remove(result.getMethod().getMethodName().toString());
			
		}
		
		extentTestThreadLocal.get().log(LogStatus.PASS, result.getMethod().getMethodName() + " Passed");
		extentReport.endTest(extentTestThreadLocal.get());
		status.put("pass", status.get("pass")+1);
	}

	public void onTestFailure(ITestResult result) {
		
		HashMaptemp.put(result.getMethod().getMethodName().toString(), extentTestThreadLocal.get());
		extentThreadLocalTemp.put(result.getMethod().getMethodName().toString(),HashMaptemp);
		ExceptionHashMaptemp.put(result.getMethod().getMethodName().toString(), exception);
		
/*		if(flag==true)
		{
		System.out.println("Finished Test: " + result.getMethod().getMethodName() + " :FAILED");
		if (exception != "")
			extentTestThreadLocal.get().log(LogStatus.FAIL,  result.getMethod().getMethodName() + "<pre><br> Exception - " + exception + "</br></pre>" + " Failed");
		else
			extentTestThreadLocal.get().log(LogStatus.FAIL,  result.getMethod().getMethodName() + " :: Failed");
		// test.addScreenCapture(screen);
		extentReport.endTest(extentTestThreadLocal.get());
		status.put("fail", status.get("fail")+1);
		}
		else
		{
			
			
			if (exception != "")
				extentTestThreadLocal.get().log(LogStatus.FAIL, result.getName() + "<pre><br> Exception " + exception + "</br></pre>" + "Failed");
			else
				extentTestThreadLocal.get().log(LogStatus.FAIL, result.getName() + " :: Failed");
			// test.addScreenCapture(screen);
			extentReport.endTest(extentTestThreadLocal.get());
					
	} */	
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("Finished Test: " + result.getName() + " :SKIPPED");
		extentTestThreadLocal.get().log(LogStatus.SKIP, result.getName() + " Skipped");
		extentReport.endTest(extentTestThreadLocal.get());
		status.put("skip", status.get("skip")+1);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Finished Test: " + result.getName() + " :FAILED BUT WITHIN SUCCESS PERCENTAGE");
		extentTestThreadLocal.get().log(LogStatus.WARNING, result.getName() + " Failed");
		extentReport.endTest(extentTestThreadLocal.get());
	}

	public void onFinish(ITestContext context) {
		System.out.println("failed hashmap size - "+ extentThreadLocalTemp.size());
		
		for(Entry<String,HashMap<String,ExtentTest>> failedcaseMap :extentThreadLocalTemp.entrySet())
		{
			String key= failedcaseMap.getKey();
			System.out.println("Key value - "+ key);
			
			HashMap<String,ExtentTest> resultTmp=failedcaseMap.getValue();
			
			System.out.println("resultTmp hashmap size - "+ resultTmp.size());
			
			for(Entry<String,ExtentTest> TempMap :resultTmp.entrySet())
			{
				if(TempMap.getKey().toString().equals(failedcaseMap.getKey().toString()))
				{
				System.out.println("key  -: "+TempMap.getKey().toString()+"\n value :"+TempMap.getValue());
				String Exception=ExceptionHashMaptemp.get(key);
			
			if (!Exception.equals("") && Exception!=null)
				TempMap.getValue().log(LogStatus.FAIL,  key + "<pre><br> Exception - " + Exception + "</br></pre>" + " Failed");
			else
				TempMap.getValue().log(LogStatus.FAIL, key + " :: Failed");
			
			// test.addScreenCapture(screen);
			extentReport.endTest(TempMap.getValue());
			status.put("fail", status.get("fail")+1);
				}
			
			
		}
			
		}
		
		
		Set<ITestResult> failedTests = context.getFailedTests().getAllResults();
		for (ITestResult temp : failedTests) {
			ITestNGMethod method = temp.getMethod();
//			IResultMap a = context.getFailedTests();
			if (context.getFailedTests().getResults(method).size() > 1) {
				failedTests.remove(temp);
			} else {
				if (context.getPassedTests().getResults(method).size() > 0) {
					failedTests.remove(temp);
				}
			}
		}
		if (extentReport != null){
			HashMap<String, HashMap <String, Integer>> reportData = new HashMap<>();
			HashMap<String, Integer> temp = new HashMap<>(status);
			reportData.put(testReportName, temp);
			
			reportStatus.add(reportData);
			
			extentReport.flush();
		}
	}

	public static String setException(String message, StackTraceElement[] stackTrace) {

		for(StackTraceElement stackTraceElement : stackTrace) {                         
			message = message + System.lineSeparator() + stackTraceElement;
		}
		exception = message;
		return exception;
	}

	public static void clearAllException() {

		exception = "";
	}
	
	public static void setMessage(String message) {

		exception = message;
	}
	
	public static void setLog(LogStatus type, String logMessage){
		extentTestThreadLocal.get().log(type, logMessage);
	}

	public static void setLogsForTestData(String scenario, Map<String, String> inputMap, String expected ){
		try{
			inputMap.remove("testdata");
		}catch(Exception ex){

		}
		//setLog(LogStatus.INFO, "TEST SCENARIO - " + scenario);
		setLog(LogStatus.INFO, "TEST DATA - " + inputMap);
		//setLog(LogStatus.INFO, "EXPECTATION - " + expected);
	}

	@Override
	public void onStart(ISuite suite) {
		startTime = DateUtil.getCurrentDateAndTime();
	}

	@Override
	public void onFinish(ISuite suite) {
		endTime = DateUtil.getCurrentDateAndTime();
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		endTime = DateUtil.getCurrentDateAndTime();
	}


}
	

