package com.test.servicefactory;

import com.story.config.Config;

public class DemoServiceFactory
{	
	private static Config config=null;
    public static Config getConfigInstance() {
			        if (config==null) {			
			            try {
			            	config = Config.getInstance();
			            } catch(Exception e) {
			            	e.printStackTrace();
			            }
			        }
			        return config;
    } // end of getConfigInstance

//}//End of DemoServiceFactory

//public void TakeScreenshot(String methodname)
//{
//takeScreenShot(methodname);
//
//}

}