package com.zalando.suite;

import java.util.Map;

import org.openqa.selenium.remote.Command;
import org.testng.annotations.Test;

import com.zalando.base.BaseClass;
import com.zalando.commonUtils.CommonUtils;
import com.zalando.commonUtils.TestPropertyLoader;
import com.zalando.exceptions.CartPageException;
import com.zalando.exceptions.ElementNotAvailableToClickException;
import com.zalando.exceptions.SearchLandingPageException;
import com.zalando.exceptions.TestDataException;
import com.zalando.page.CartPage;
import com.zalando.page.HomePage;
import com.zalando.page.LoginPage;
import com.zalando.page.ProductLandingPage;
import com.zalando.page.SearchLandingPage;

public class TestBuyProducts extends BaseClass {

	@Test
	public void testAddToCart() throws TestDataException, ElementNotAvailableToClickException, SearchLandingPageException, InterruptedException, CartPageException{

		HomePage homePage =  new HomePage();
		
		LoginPage loginPage = new LoginPage();

		SearchLandingPage searchResults = new SearchLandingPage();

		ProductLandingPage productPage = new ProductLandingPage();
		
		CartPage cartPage = new CartPage();

		String className = this.getClass().toString();

		CommonUtils.loginAndMaximise(driver, TestPropertyLoader.APP_URL);
		
		//Login to the Account
		
		loginPage.login(driver, testData.get("Username"), testData.get("Password"));

		//Adding Items to Cart

		String searchString = testData.get("BuyProduct");

		if(searchString == null || searchString.isEmpty()){

			log.error("No product configured to search. Please check the test data");

			throw new TestDataException("No product configured to search. Please check the test data");

		}

		boolean productAdded = false;

		String[] searchProducts = searchString.split(";;");

		//Adding product 

		for(int count=0; count < searchProducts.length ; count++){

			String eachProduct = searchProducts[count];

			eachProduct = eachProduct.trim();
			
			if(eachProduct.isEmpty()){
				
				continue;
				
			}

			log.info("Searching the product : "+eachProduct);

			homePage.searchProduct(driver, eachProduct);

			log.info("Checking if the product is valid");

			if(searchResults.isSearchedProductValid(driver)){

				log.info("Product found. Checking if the product is on stock");

				if(productPage.checkIfInStock(driver)){

					log.info("Adding the product to Cart");

					productPage.addToBag(driver);

					productAdded = true;

					break;

				}

				else{

					log.error("The product : "+eachProduct+" is not in stock");

					productAdded = false;
				}

			}

			else{

				log.error("The product : "+eachProduct+" is invalid. Search returned no results");

				continue;

			}

			//Coming back to home page for next iteration

			productPage.clickHomeLogo(driver);

		}

		if(!productAdded){

			log.error("Not even a single product could be added to cart. Check the test data");

			throw new TestDataException("Not even a single product could be added to cart. Check the test data");

		}

		productPage.clickCart(driver);
		
		cartPage.selectQuantity(driver, testData.get("Quantity"));
		
		cartPage.moveToWishList(driver);

	}



}
