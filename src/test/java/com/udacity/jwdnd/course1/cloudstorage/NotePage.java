package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {
    @FindBy(css = "#add-note")
    private WebElement addNote;

    //returns first matching
    @FindBy(css = ".edit-note")
    private WebElement editNote;

    //deletes first matching
    @FindBy(css = ".delete-note")
    private WebElement deleteNote;

    @FindBy(css = "#note-title")
    private WebElement notetitle;

    @FindBy(css = "#note-description")
    private WebElement notedescription;

    @FindBy(css = "#note-close")
    private WebElement noteClose;

    @FindBy(css = "#save-note")
    private WebElement notesubmit;

    @FindBy(css = "#nav-notes-tab")
    private WebElement noteTab;

    @FindBy(css = "#success-resume")
    private WebElement successPage;

    @FindBy(css = "#noteModal")
    private WebElement noteModal;

    private WebDriver driver;

    private void helperWait(WebElement element) throws InterruptedException {
        Thread.sleep(1000);
        new WebDriverWait(this.driver, 5).until(
                ExpectedConditions.visibilityOf(element)
        );
    }

    private void helperWaitAndClick(WebElement element) throws InterruptedException {
       Thread.sleep(1000);
       new WebDriverWait(this.driver, 5).until(
                ExpectedConditions.elementToBeClickable(element)
       ).click();
    }

    private void openNoteTab() throws InterruptedException {
            this.helperWaitAndClick(this.noteTab);
    }

    private void helperPopulateFieldsAndSave(String notetitle, String notedescription){
        this.notetitle.clear();
        this.notetitle.sendKeys(notetitle);
        this.notedescription.clear();
        this.notedescription.sendKeys(notedescription);
        this.notesubmit.click();
    }

    private void returnNoteHome() throws InterruptedException {
        this.helperWaitAndClick(this.successPage);
        this.openNoteTab();
    }

    public NotePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void createNote(String notetitle, String notedescription) throws InterruptedException {
        this.openNoteTab();
        this.helperWaitAndClick(this.addNote);
        //going to addNote
        this.helperWait(this.noteModal);
        this.helperPopulateFieldsAndSave(notetitle, notedescription);
        this.returnNoteHome();
    }

    public void updateNote(String notetitle, String notedescription) throws InterruptedException {
        this.openNoteTab();
        this.helperWaitAndClick(this.editNote);
        this.helperWait(this.noteModal);
        this.helperPopulateFieldsAndSave(notetitle, notedescription);
        this.returnNoteHome();
    }

    public void deleteNote() throws InterruptedException {
        this.openNoteTab();
        this.helperWaitAndClick(this.deleteNote);
        this.returnNoteHome();
    }
}
