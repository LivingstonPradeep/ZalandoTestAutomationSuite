package com.zalando.page;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.zalando.exceptions.CartPageException;
import com.zalando.exceptions.ElementNotAvailableToClickException;

public class CartPage extends ObjectRepository{
	
	public static final String xpathQuantitySelect = "//select[contains(@class,'quantitySelect')]";
	
	public static final String xpathMoveToWishList = "//span[contains(@class,'wishlistText')]";
	
	public static final String xpathAlreadyInWishlist = "//span[text()='Auf dem Wunschzettel']";
	
	

	public void selectQuantity(RemoteWebDriver driver, String quantity) throws InterruptedException {
		
		log.info("Selecting the quantity");	
		
		Select selectQuantity = new Select(driver.findElement(By.xpath(xpathQuantitySelect)));
		
		selectQuantity.selectByVisibleText(quantity);
		
		Thread.sleep(3000);
	
	}
	
	public void moveToWishList(RemoteWebDriver driver) throws CartPageException {
		
		log.info("Moving to wishlist");	
		
		if(driver.findElements(By.xpath(xpathMoveToWishList)).size() == 0){
			
			if(driver.findElements(By.xpath(xpathAlreadyInWishlist)).size() > 0){
				
				log.info("Product Already in wishlist");	
				
			}
			else{
				throw new CartPageException("Cannot add the product to wishlist");
			}
			
		}
		else{
			driver.findElement(By.xpath(xpathMoveToWishList)).click();
		}
		
		
		
	}
	
	

}
