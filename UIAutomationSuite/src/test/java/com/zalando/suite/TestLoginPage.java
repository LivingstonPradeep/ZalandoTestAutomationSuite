package com.zalando.suite;

import org.openqa.selenium.Dimension;
import org.testng.annotations.Test;

import com.zalando.base.BaseClass;
import com.zalando.commonUtils.TestPropertyLoader;

public class TestLoginPage extends BaseClass{
	
	@Test				
	public void testLogin() {	
		
		driver.get(TestPropertyLoader.APP_URL);
		driver.manage().window().maximize();
		
		
	}	
	
	
}
