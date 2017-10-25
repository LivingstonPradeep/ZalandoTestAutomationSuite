package com.zalando.suite;

import java.util.Map;

import org.openqa.selenium.remote.Command;
import org.testng.annotations.Test;

import com.zalando.base.BaseClass;
import com.zalando.commonUtils.CommonUtils;
import com.zalando.commonUtils.TestPropertyLoader;
import com.zalando.exceptions.ElementNotAvailableToClickException;
import com.zalando.exceptions.SearchLandingPageException;
import com.zalando.exceptions.TestDataException;
import com.zalando.page.HomePage;
import com.zalando.page.ProductLandingPage;
import com.zalando.page.SearchLandingPage;

public class TestBuyProductsBackup extends BaseClass {

	@Test
	public void testAddToCart() throws TestDataException, ElementNotAvailableToClickException, SearchLandingPageException, InterruptedException{

		HomePage homePage =  new HomePage();

		SearchLandingPage searchLandingPage =  new SearchLandingPage();

		ProductLandingPage productPage = new ProductLandingPage();

		String className = this.getClass().toString();

		//		Map<String, String> testData = gette

		CommonUtils.loginAndMaximise(driver, TestPropertyLoader.APP_URL);

//		homePage.continueWithUK(driver);

		//Adding Multiple items to the cart

		log.info("Adding Multiple items to the cart");

		String searchString = testData.get("BuyProducts");

		if(searchString == null || searchString.isEmpty()){

			log.error("No product configured to search. Please check the test data");

			throw new TestDataException("No product configured to search. Please check the test data");

		}

		String[] searchProducts = searchString.split(";");

		//Adding all the products to the cart


		for(int count=0; count < searchProducts.length ; count++){

			String eachProduct = searchProducts[count];

			eachProduct = eachProduct.trim();

			log.info("Adding the product "+eachProduct+" to the cart");

			if(eachProduct.endsWith(")")){
				String[] productArray = eachProduct.split("\\(");

				String productName = productArray[0];

				String productSize = (productArray[1]).replaceAll("\\)", "");

				homePage.searchProduct(driver, productName);

				searchLandingPage.selectFirstSearchHit(driver);

				productPage.chooseSize(driver,productSize);


			}

			else{

				homePage.searchProduct(driver, eachProduct);

				searchLandingPage.selectFirstSearchHit(driver);

				log.info("Selecting the product to bag "+eachProduct);
				productPage.addToBag(driver);

				productPage.clickHomeLogo(driver);

			}

		}

	}

}
