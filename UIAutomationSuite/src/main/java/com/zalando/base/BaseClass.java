package com.zalando.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.zalando.commonUtils.TestPropertyLoader;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


/*
 * BaseClass - Parent class which contains the Preprocessing and post processing methods
 * Also contains the common variables
 * 
 */
public class BaseClass implements BaseConstants{

	public RemoteWebDriver driver = null;
	public String currentOs = System.getProperty(osName);
	public Properties filePathProperties = new Properties();
	protected Map<String, String> testData;



	protected static Logger log=Logger.getLogger(BaseClass.class);

	@BeforeSuite
	public void initialSetup(){

		String log4jConfPath = "./Log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);

		//		BasicConfigurator.configure();

		//Any Preprocessing before starting the Suite
	}

	/* 
	 * This method is used to initialize the web driver
	 * before running every test
	 */

	@BeforeMethod(alwaysRun = true)
	public void beforeTest() throws IOException, BiffException {

		initialize(TestPropertyLoader.BROWSER, TestPropertyLoader.VERSION,
				TestPropertyLoader.PLATFORM);

		String[] classNameSplit = this.getClass().getName().split("\\.");
		String testName = classNameSplit[classNameSplit.length-1];

		testData = getTestData(testName);
	}

	/* 
	 * This method is used to close the webdriver
	 * and other teardown tasks
	 */ 
	@AfterMethod(alwaysRun = true)
	public void afterTestMethod() {
		//Closing the web driver

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
	}

	/**
	 * should be called for every test before execution. it initialises the
	 * properties files and the defines the constants User name, Password and
	 * the browser
	 * 
	 * @throws IOException
	 */
	private void initialize(String browser, String version, String platform)
			throws IOException {

		//Define the Browser
		loadFilePathProperties();

		driver = getDriverForBrowser(browser,version,platform);

	}


	/**
	 * Loads the filepaths from the property file
	 * @return
	 */
	private Properties loadFilePathProperties() {

		try {
			filePathProperties.load(new FileInputStream(TestPropertyLoader.FILE_PATH));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return filePathProperties;

	}

	/**
	 * This method will fetch the Remote WebDriver based on type of browser specified
	 * @param browser
	 * @param version
	 * @param platform
	 * @return
	 */

	public synchronized RemoteWebDriver getDriverForBrowser(String browser, String version, String platform) {

		//Set the platform (windows/linux) in the desiredCapabilities object


		if(browser.equalsIgnoreCase(internetExplorer) || StringUtils.containsIgnoreCase(browser, explorer)){

			// IE is available only in Windows

			if(!StringUtils.containsIgnoreCase(currentOs, windows)){
				log.info("IE available only in Windows OS. Creating Chrome Driver instead");

				driver = createChromeDriver(currentOs);

			}
			else{
				driver = createIEDriver();
			}


		}
		else if(browser.equalsIgnoreCase(chrome)){

			driver = createChromeDriver(currentOs);
		}


		return driver;
	}



	private RemoteWebDriver createChromeDriver(String os) {

		log.info("Creating Chrome Driver");
		os = os.toLowerCase();

		String chromeBaseDir = "";

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setBrowserName("chrome");

		if(StringUtils.containsIgnoreCase(os, windows)){

			capabilities.setPlatform(Platform.WINDOWS);

			chromeBaseDir = System.getProperty("user.dir")+File.separator+filePathProperties.getProperty("Drivers_Folder")+File.separator+filePathProperties.getProperty("Chrome_Driver_win");
		}
		else if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0 ){
			chromeBaseDir = System.getProperty("user.dir")+File.separator+filePathProperties.getProperty("Drivers_Folder")+File.separator+filePathProperties.getProperty("Chrome_Driver_unix");
		}


		System.setProperty(chromeDriver,chromeBaseDir);


		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);

		log.info("Setting Chrome Capabilities and initiating Chrome Driver");

		//		return new ChromeDriver(capabilities);

		return new ChromeDriver(options);

	}



	private RemoteWebDriver createIEDriver(){

		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

		System.setProperty(ieDriver, System.getProperty("user.dir")+File.separator+filePathProperties.getProperty("Drivers_Folder")+File.separator+filePathProperties.getProperty("IE_Driver"));

		desiredCapabilities.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
		desiredCapabilities.setCapability("ignoreZoomSetting", true);
		desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
		try {
			return new RemoteWebDriver(desiredCapabilities);
		} catch (WebDriverException e) {
			e.printStackTrace();
		}

		return null;
	}

	/*
	 * getTestData method returns the Map of the test data
	 */

	private Map<String,String> getTestData(String testName) throws BiffException, IOException{

		int rowNum = 0;

		Map<String,String> testData = new HashMap<String, String>();

		Sheet dataSheet = Workbook.getWorkbook(new File(TestPropertyLoader.TEST_DATA_SHEET)).getSheet("suite");

		log.info("Getting the test data from the test data sheet");

		rowNum=dataSheet.findCell(testName).getRow();

		if(rowNum <= 0){
			log.error("The test data could not be found for the test case "+testName);
		}

		for (int count =0; count<dataSheet.getColumns(); count++) {
			String key = dataSheet.getCell(count,0).getContents();
			String value = dataSheet.getCell(count,rowNum).getContents();
			testData.put(key, value);
		}


		return testData;

	}

}
