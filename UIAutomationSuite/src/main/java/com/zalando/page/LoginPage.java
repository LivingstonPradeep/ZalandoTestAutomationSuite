package com.zalando.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.zalando.commonUtils.CommonUtils;
import com.zalando.exceptions.ElementNotAvailableToClickException;

public class LoginPage extends ObjectRepository{

	public static final String xpathloginEmail= "//input[@name='login.email']";

	public static final String xpathloginPassword= "//input[@name='login.password']";

	public static final String xpathloginSubmitButton = "//button[contains(@class,'login_button')]";


	public void login(RemoteWebDriver driver, String userName, String passWord) throws ElementNotAvailableToClickException, InterruptedException{

		log.info("Clicking On Login Icon");

		CommonUtils.clickElementbByXpath(driver, xpathLoginIcon);
		
		Thread.sleep(2000);

		CommonUtils.clickElementbByXpath(driver, xpathLoginButton);

		CommonUtils.waitForElementToBeClickable(driver, xpathloginSubmitButton);

		WebElement userNameField = driver.findElement(By.xpath(xpathloginEmail));

		userNameField.clear();

		userNameField.sendKeys(userName);

		WebElement passwordField = driver.findElement(By.xpath(xpathloginPassword));

		passwordField.clear();

		passwordField.sendKeys(passWord);

		CommonUtils.clickElementbByXpath(driver, xpathloginSubmitButton);
		
		Thread.sleep(2000);

	}


}
