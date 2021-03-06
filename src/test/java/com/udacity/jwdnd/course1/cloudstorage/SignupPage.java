package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(css = "#inputFirstName")
    private WebElement firstNameElement;

    @FindBy(css = "#inputLastName")
    private WebElement lastNameElement;

    @FindBy(css = "#inputUsername")
    private WebElement userNameElement;

    @FindBy(css = "#inputPassword")
    private WebElement passwordElement;

    @FindBy(css = "#submit")
    private WebElement submitButton;

    public SignupPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }

    public void signup(String firstName, String lastName, String username, String password){
        this.firstNameElement.sendKeys(firstName);
        this.lastNameElement.sendKeys(lastName);
        this.userNameElement.sendKeys(username);
        this.passwordElement.sendKeys(password);
        this.submitButton.click();
    }
}
