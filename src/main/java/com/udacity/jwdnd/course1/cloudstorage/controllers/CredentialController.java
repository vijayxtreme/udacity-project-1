package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class CredentialController {

    private CredentialService credentialService;

    //DI
    CredentialController(CredentialService credentialService){
        this.credentialService = credentialService;
    }

    @PostMapping("/addCredential")
    public String addCredential(){
        //save url, username and password plus hashed password (check encryption)
        return "result";
    }

    @DeleteMapping("/deleteCredential/{id}")
    public String deleteCredential(){
        //get request param, look up in service, then delete if exists
        return "result";
    }
}
