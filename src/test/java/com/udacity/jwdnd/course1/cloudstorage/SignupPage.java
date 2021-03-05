package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    public SignupPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
    }
}
