package com.zalando.commonUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestPropertyLoader {
	
	private static Properties properties;
	
	public static final String BROWSER = getProperty("browser", "chrome");
	public static final String VERSION = getProperty("version", "1.0");
	public static final String PLATFORM = getProperty("platform", "WINDOWS");
	public static final String HOST = getProperty("host", "localhost");
	public static final int PORT = Integer.parseInt(getProperty("port", "8080"));
	public static final String APP_URL = getProperty("app.url", "https://www.google.co.in/");
	public static final String FILE_PATH =  getProperty("filepath", "files.properties");
	public static final String TEST_DATA_SHEET =  getProperty("testData.sheet", "Test_Data_Sheet.xls");
	public static final String OS = getProperty("os");
	
	public static String getProperty(String key, String defaultValue) {
		if (properties == null) {
			try {
				properties = new Properties();
				properties.load(new FileReader("test.properties"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return properties.getProperty(key, defaultValue);
	}
	
	public static String getProperty(String key) {
		if (properties == null) {
			try {
				properties = new Properties();
				properties.load(new FileReader("test.properties"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return properties.getProperty(key);
	}
	
	
}
