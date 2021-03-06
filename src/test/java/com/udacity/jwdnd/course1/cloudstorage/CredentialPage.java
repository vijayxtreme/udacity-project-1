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

    @FindBy(css = "#save-credential")
    private WebElement saveCredential;

    @FindBy(css = "#success-resume")
    private WebElement successPage;


    @FindBy(css = "#credentialModal")
    private WebElement credentialModal;


    private WebDriver driver;
    private static Helper helper;



    public CredentialPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    private void openCredentialTab() throws InterruptedException {
        helper.helperWaitAndClick(this.driver, this.credentialTab);
    }

    private void returnCredentialHome() throws InterruptedException {
        helper.helperWaitAndClick(this.driver, this.successPage);
        this.openCredentialTab();
    }

    private void populateFieldsAndSave(String url, String username, String password) throws InterruptedException {
        this.url.clear();
        this.url.sendKeys(url);
        this.userName.clear();
        this.userName.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);
        this.saveCredential.click();
        this.returnCredentialHome();
    }

    public void createCredential(String url, String username, String password) throws InterruptedException {
        this.openCredentialTab();
        helper.helperWaitAndClick(this.driver, this.addNewCredential);
        helper.helperWait(this.driver, this.credentialModal);
        this.populateFieldsAndSave(url, username, password);
    }

    public void updateCredential(String url, String username, String password) throws InterruptedException {
        this.openCredentialTab();
        helper.helperWaitAndClick(this.driver, this.editCredential);
        helper.helperWait(this.driver, this.credentialModal);
        this.populateFieldsAndSave(url, username, password);
    }

    public void deleteCredential() throws InterruptedException {
        this.openCredentialTab();
        helper.helperWaitAndClick(this.driver, this.deleteCredential);
    }
}
