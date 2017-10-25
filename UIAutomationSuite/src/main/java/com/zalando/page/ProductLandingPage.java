package com.zalando.page;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.zalando.commonUtils.CommonUtils;
import com.zalando.exceptions.ElementNotAvailableToClickException;
import com.zalando.exceptions.SearchLandingPageException;

public class ProductLandingPage extends ObjectRepository{

//	public static final String xpathAddToBag = "/html/body/div[2]/div[4]/div/div/div/z-grid[1]/z-grid-item[2]/div[4]/button";
	
	public static final String xpathAddToBag = "//*[@id='z-pdp-topSection-addToCartButton']";

	public static final String xpathSelectSize =  "//*[@id='zvui-size-select-dropdown-8298-value']/div";

	public static final String xpathChooseYourSize =  "//div[text()='Choose your size']";
	
	
	public static final String xpathProductSoldOut =  "//*[text()='Ausverkauft']";
			

	public void chooseSize(RemoteWebDriver driver, String productSize) throws ElementNotAvailableToClickException {

		CommonUtils.clickElementbByXpath(driver, xpathChooseYourSize);

	}


	public void addToBag(RemoteWebDriver driver) throws ElementNotAvailableToClickException {

		CommonUtils.scrollTillElementView(driver, xpathAddToBag);
		
		CommonUtils.clickElementbByXpath(driver, xpathAddToBag);
		
		log.info("Product Added to bag");

	}


	public boolean checkIfInStock(RemoteWebDriver driver) {
		
		// Checking if add to cart option is enabled
		
		if(driver.findElements(By.xpath(xpathAddToBag)).size() == 0){
			
			//Check if product is sold out
			if(driver.findElements(By.xpath(xpathProductSoldOut)).size() > 0){
				
				log.info("The product is sold out");
			}
			
			//TODO Check if product is discontinued or other conditions
			
			
			return false;
			
		}
		
		else {
			
			return true;
		}
	}


}
