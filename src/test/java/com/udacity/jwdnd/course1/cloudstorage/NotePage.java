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

    @FindBy(css = "#note-title")
    private WebElement notetitle;

    @FindBy(css = "#note-description")
    private WebElement notedescription;

    @FindBy(css = "#save-note")
    private WebElement notesubmit;

    @FindBy(css = "#nav-notes-tab")
    private WebElement noteTab;

    private WebDriver driver;

    public NotePage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void createNote(String notetitle, String notedescription){
        WebElement webEl = new WebDriverWait(this.driver, 2).until(ExpectedConditions.elementToBeClickable(this.noteTab));

        this.noteTab.click();

        WebElement addNoteEl = new WebDriverWait(this.driver, 2).until(ExpectedConditions.elementToBeClickable(this.addNote));
        this.addNote.click();
        WebElement noteTitleEl = new WebDriverWait(this.driver, 2).until(ExpectedConditions.elementToBeClickable(this.notetitle));


        this.notetitle.sendKeys(notetitle);
        this.notedescription.sendKeys(notedescription);

        this.notesubmit.click();
    }
}
