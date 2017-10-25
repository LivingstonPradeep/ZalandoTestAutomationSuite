package com.zalando.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.zalando.commonUtils.CommonUtils;
import com.zalando.exceptions.ElementNotAvailableToClickException;

public class HomePage extends ObjectRepository{
	
	
	public static final String xpathMoveToDeutsche = "//*[@id='html']/body/div[10]/div/ul/li[2]";
	
	public static final String xpathSearchContentTab = "//*[@id='searchContent']";
	
//	public static final String xpathSubmitSearchButton = "//form[@role='search']/button";
	
	public static final String xpathSubmitSearchButton = "//*[@id='searchButtonTopSubmit']";
	
	
	public void searchProduct(RemoteWebDriver driver, String searchTerm) throws ElementNotAvailableToClickException {
		
		log.info("Searching for product "+ searchTerm);
		
		CommonUtils.clickElementbByXpath(driver, xpathSearchContentTab);
		
		WebElement searchTab = driver.findElement(By.xpath(xpathSearchContentTab));
		
		searchTab.sendKeys(searchTerm);
		
		CommonUtils.clickElementbByXpath(driver, xpathSubmitSearchButton);
	}
	
	
}
