package com.zalando.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.zalando.commonUtils.CommonUtils;
import com.zalando.exceptions.ElementNotAvailableToClickException;

public class ObjectRepository {
	
	protected static Logger log=Logger.getLogger(ObjectRepository.class);
	
	public static final String xpathHomeLogo = "//a[@name='header.logo']";
	
	public static final String xpathCartIcon = "//a[@href='/cart/']";
	
	public static final String xpathLoginIcon = "//*[@id='fieldAccountAccountBox']";
	
	public static final String xpathLoginButton = "//*[@id='fieldLogin']";
	
	
	
	public void clickHomeLogo(RemoteWebDriver driver) throws ElementNotAvailableToClickException{
		log.info("Clicking On Home Logo");
		
		CommonUtils.clickElementbByXpath(driver, xpathHomeLogo);
	}
	
	public void clickCart(RemoteWebDriver driver) throws ElementNotAvailableToClickException{
		
		CommonUtils.scrollTillElementView(driver, xpathCartIcon);
		
		
		Actions actions = new Actions(driver);
		
		actions.moveToElement(driver.findElement(By.xpath(xpathCartIcon)));
		
		log.info("Clicking On Cart Icon");
		
		CommonUtils.clickElementbByXpath(driver, xpathCartIcon);
	}
	
	

}
