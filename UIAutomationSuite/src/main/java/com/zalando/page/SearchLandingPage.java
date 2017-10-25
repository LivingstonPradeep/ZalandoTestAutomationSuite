package com.zalando.page;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.zalando.commonUtils.CommonUtils;
import com.zalando.exceptions.ElementNotAvailableToClickException;
import com.zalando.exceptions.SearchLandingPageException;


public class SearchLandingPage  extends ObjectRepository{
	
	public static final String xpathSearchGrid = "(//*[@id='z-nvg-cognac-root']/z-grid/z-grid-item[2]/div/z-grid[3]/z-grid-item/div/a)";
	
	public static final String xpathSearchProductNotFound = "//p[text()='Leider konnten wir keine passenden Produkte finden']";

	public void selectFirstSearchHit(RemoteWebDriver driver) throws SearchLandingPageException, ElementNotAvailableToClickException {

		List<WebElement> searchHits = driver.findElements(By.xpath(xpathSearchGrid));
		
		log.info("Selecting the first hit");
		
		if(searchHits.isEmpty()){
			log.error("No search hits found");
			
			throw new SearchLandingPageException("No search hits found");
		}
		
		//getting the first item in the list can be any hit so finding the exact element we want
		
		CommonUtils.clickElementbByXpath(driver, xpathSearchGrid+"[1]");
		
		
	}

	public boolean isSearchedProductValid(RemoteWebDriver driver) {
		
		return (driver.findElements(By.xpath(xpathSearchProductNotFound)).size() == 0);
		
	}
	
	

}
