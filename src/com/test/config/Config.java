package com.test.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.story.request.InitialiseDriver;
import com.story.servicefactory.DemoServiceFactory;


public class Config {
				private static  Config instance = null;
				private Properties globalConfigs;
				private Properties domainConfigs;
				private String domain = "debug";
				public static String ext=".cfg";
				
				/**
				 * This function will load the configuration file.
				 * @throws Exception
				 */
				public static void loadConfigFile() throws Exception {
			        loadConfigFile(getConfigLocation());
			    }
				/**
				 * This method is used to read regression file so that environment could be define.
				 * @param configLocation
				 *           this parameter contains the location Of Regression.cfg file in which environment is define.
				 * @throws Exception
				 */
				private static void  loadConfigFile(String configLocation) throws Exception   {	
					            String globalConfigFile=configLocation+"Regression.cfg";
					            instance = new Config();
					            instance.globalConfigs = new Properties();
					            instance.globalConfigs.load(new FileInputStream(globalConfigFile));
					            if(instance.globalConfigs.get("domain")!=null)  {
					                instance.domain=instance.globalConfigs.get("domain").toString();
					            }
					            String domainConfigFile=configLocation+InitialiseDriver.environment+ext;
					          //  String domainConfigFile=configLocation+instance.domain+ext;
					            instance.domainConfigs = new Properties();
					            instance.domainConfigs.load(new FileInputStream(domainConfigFile));
					            System.out.println("env config file is "+domainConfigFile);
				} // end  of loadConfigFile function.
				
				/**
				 * This method gets the test environment from regression file
				 * @param configLocation
				 *       - This contains the location of regression.cfg file.
				 * @return
				 *     -It returns a String which contains the environment value.
				 * @throws Exception
				 */
				public static String getEnvironment(String configLocation) throws Exception  {	
					            String globalConfigFile=configLocation+"Regression.cfg";
					            instance = new Config();
					            instance.globalConfigs = new Properties();
					            instance.globalConfigs.load(new FileInputStream(globalConfigFile));
					            if(instance.globalConfigs.get("domain")!=null) 
					            {
					                instance.domain=instance.globalConfigs.get("domain").toString();
					            }
					            return instance.domain; 
				} // end of getEnvironment function.
				
				/**
				 * 
				 * @return
				 * @throws Exception
				 */
				public static Config getInstance() throws Exception {
			        			return getInstance(getConfigLocation());	
				} // end of getInstance function.
				
				/**
				 * This function return the the config Object for with config file values
				 * @param configLocation
				 *    - This contains the value of regression.cfg file location.
				 * @return
				 *    -It return the config object with loaded config values
				 * @throws Exception
				 */
				public static Config getInstance(String configLocation) throws Exception  {
					            if(instance==null)
					            {
					                loadConfigFile(configLocation);
					            }
					            return instance;
				} // end of getInstance function.
				
				/**
				 * 
				 * @param key
				 * @return
				 * @throws Exception
				 */
				public String getConfig(String key) throws Exception {
								if(instance==null) {
						                    	throw new RuntimeException("Initialize AppConfig");
						          }
								Object value=null;
								value=domainConfigs.get(key);
								if (value==null)  {
												value=globalConfigs.get(key);
								}
								if (value!=null)   {
												return value.toString();
						         }
								else  {
												return null;
						        }
				}  // end of getInstance function.
	
				public static String getConfigLocation() {
								String path = System.getProperty("user.dir");
								path=path+"/src/com/story/config/";
								return path;
			    } // end of getConfigLocation method
				
				public static void main(String[] args) throws Exception  {
								Config appConfig = Config.getInstance();
								System.out.println("appConfig.getConfig(key)===="+appConfig.getConfig("Endpoint.url"));
			    }
				
				/**
				 * This method get the environment name from Regression File
				 * @return String
				 * 					environment_name
				 * @throws FileNotFoundException
				 * 					If Regression.cfg not present in desire location
				 * @throws IOException
				 * 					if Regression.cfg is in unreadable form
				 */
/*				public String getEnvironment() throws FileNotFoundException, IOException {
								String globalConfigFile=getConfigLocation()+"Regression.cfg";
					            DemoServiceFactory.getConfigInstance().globalConfigs = new Properties();
					            DemoServiceFactory.getConfigInstance().globalConfigs.load(new FileInputStream(globalConfigFile));
					            if(DemoServiceFactory.getConfigInstance().globalConfigs.get("domain")!=null) {
								                DemoServiceFactory.getConfigInstance().domain=
								                        DemoServiceFactory.getConfigInstance().globalConfigs.get("domain").toString();
					            }// end of if block
					            return DemoServiceFactory.getConfigInstance().domain;
				}  */ // end of getEnvironment method
}// end of Config class
