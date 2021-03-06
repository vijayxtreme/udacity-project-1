package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(css = "#logout")
    private WebElement logoutButton;

    private WebDriver driver;

    private void helperWaitAndClick(WebElement element) throws InterruptedException {
        Thread.sleep(1000);
        new WebDriverWait(this.driver, 5).until(
                ExpectedConditions.elementToBeClickable(element)
        ).click();
    }

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void logout() throws InterruptedException {
        this.helperWaitAndClick(this.logoutButton);
    }
}
