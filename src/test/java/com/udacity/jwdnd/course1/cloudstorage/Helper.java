package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helper {
    public static void helperWait(WebDriver driver, WebElement element) throws InterruptedException {
        Thread.sleep(1000);
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.visibilityOf(element)
        );
    }

    public static void helperWaitAndClick(WebDriver driver, WebElement element) throws InterruptedException {
        Thread.sleep(1000);
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.elementToBeClickable(element)
        ).click();
    }
}