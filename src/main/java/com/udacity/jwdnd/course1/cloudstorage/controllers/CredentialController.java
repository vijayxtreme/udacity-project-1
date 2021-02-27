package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class CredentialController {

    private CredentialService credentialService;

    //DI
    CredentialController(CredentialService credentialService){
        this.credentialService = credentialService;
    }

    @PostMapping("/addCredential")
    public String addCredential(Credentials credential){
       // System.out.println("***----CREDENTIAL----***");
       // System.out.println(credential);
       // System.out.println("***----CREDENTIAL----***");
        credentialService.createCredential(credential);

        //save url, username and password plus hashed password (check encryption)
        return "result";
    }

    @GetMapping("/deleteCredential/{id}")
    public String deleteCredential(@PathVariable String id){

        credentialService.deleteCredential(id);
        return "result";
    }
}
