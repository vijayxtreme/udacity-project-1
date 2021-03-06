package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    @FindBy(css = "#credential-url")
    private WebElement url;

    @FindBy(css = "#credential-username")
    private WebElement userName;

    @FindBy(css = "#credential-password")
    private WebElement password;

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(css = "#add-credential")
    private WebElement addNewCredential;

    //First one
    @FindBy(css = ".edit-credential")
    private WebElement editCredential;

    //First one
    @FindBy(css = ".delete-credential")
    private WebElement deleteCredential;

    @FindBy(css = "#credential-close")
    private WebElement closeModal;

    private WebDriver driver;

    public CredentialPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    private void helperWait(WebElement element){
        WebElement webEl = new WebDriverWait(this.driver, 2).until(ExpectedConditions.elementToBeClickable(element));
    }

    private void helperPopulateFieldsAndSave(String url, String username, String password){
        this.url.sendKeys(url);
        this.userName.sendKeys(username);
        this.password.sendKeys(password);
        this.addNewCredential.click();
    }

    private void openCredentialTab(){
        this.helperWait(this.credentialTab);
        this.credentialTab.click();
    }

    public void createCredential(String url, String username, String password){
        this.openCredentialTab();
        this.helperWait(this.addNewCredential);
        this.addNewCredential.click();
        this.helperPopulateFieldsAndSave(url, username, password);
        this.closeModal.click();
    }

    public void updateCredential(String url, String username, String password){
        this.openCredentialTab();
        this.helperWait(this.editCredential);
        this.editCredential.click();
        this.helperPopulateFieldsAndSave(url, username, password);
        this.closeModal.click();
    }

    public void deleteCredential(){
        this.openCredentialTab();
        this.helperWait(this.deleteCredential);
        this.deleteCredential.click();
    }
}
