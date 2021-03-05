package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(css = "#inputUsername")
    private WebElement usernameElement;

    @FindBy(css = "#inputPassword")
    private WebElement passwordElement;

    @FindBy(css = "#submit")
    private WebElement submitButton;

    public LoginPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password){
        this.usernameElement.sendKeys(username);
        this.passwordElement.sendKeys(password);
        this.submitButton.click();
    }
}

