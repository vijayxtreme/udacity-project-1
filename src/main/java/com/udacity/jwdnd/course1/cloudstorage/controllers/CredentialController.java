package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String addCredential(Credentials credential, Model model){
        String error = "";

        //if some err
        //model.addAttribute("error", "That credential already exists");

        credentialService.createCredential(credential);
        model.addAttribute("success", "Successfully added credential");

        return "result";
    }

    @GetMapping("/decrypt/{id}")
    public ResponseEntity<String> decrypt(@PathVariable String id){
        try {
            String pass = credentialService.decrypt(id);
            System.out.println("-------");
            System.out.println(pass);
            return new ResponseEntity<>(pass, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deleteCredential/{id}")
    public String deleteCredential(@PathVariable String id){

        credentialService.deleteCredential(id);
        return "result";
    }
}
