package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
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

    public static boolean elementIsVisible(WebDriver driver, WebElement element, String string) throws InterruptedException {
        WebElement el = new WebDriverWait(driver, 5).until(
                ExpectedConditions.visibilityOf(element)
        );
        String elText = el.getText();
        Boolean result = elText.equals(string);
        return result;
    }

    public static boolean elementIsNotVisible(WebDriver driver, WebElement element, String string) throws InterruptedException {
        Boolean result = false;

        try {
            //if found an element, check to see if the text is the same -- e.g. deleted element, but next in line
            //is visible
            WebElement el = new WebDriverWait(driver, 5).until(
                    ExpectedConditions.visibilityOf(element)
            );
            String elText = el.getText();
            //if text is same, then we didn't delete the element. expect that the new element has different text
            if(elText.equals(string)){
                //then element is still visible, return result (false by default)
                return result;
            }
        }catch(Exception e){
            //if our web driver fails finding the element, then true - element not visible
            result = true;
        }finally {
            return result;
        }

    }
}
