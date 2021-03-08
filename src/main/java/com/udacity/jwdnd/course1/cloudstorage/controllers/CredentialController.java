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

        //prevent against non filled out forms
        if(credential.getUserid() == null || credential.getUsername() == "" || credential.getPassword() == "" || credential.getUrl() == "" ){
            model.addAttribute("error", "Credential fields not completely filled out, please try again.");
            return "result";
        }

        try {
            if (credential.getCredentialid() != null) {
                credentialService.updateCredential(credential);
                model.addAttribute("success", "Successfully updated credential");
            } else {
                credentialService.createCredential(credential);
                model.addAttribute("success", "Successfully added credential");
            }
        }catch(Exception exception){
            model.addAttribute("error", "Something went wrong with saving your credential, please try later.");
        }finally {
            return "result";
        }

    }

    @GetMapping("/decrypt/{id}")
    public ResponseEntity<String> decrypt(@PathVariable String id){
        try {
            String pass = credentialService.decrypt(id);
            return new ResponseEntity<>(pass, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deleteCredential/{id}")
    public String deleteCredential(@PathVariable String id, Model model){
        if(id == ""){
            model.addAttribute("error", "There was an issue with your request, please try later");
            return "result";
        }
        try {
            credentialService.deleteCredential(id);
            model.addAttribute("success", "Successfully deleted credential");

        }catch(Exception e){
            model.addAttribute("error", "There was an issue with your request, please try later");

        }finally {
            return "result";
        }
    }
}
