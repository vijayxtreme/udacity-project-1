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

        if(credential.getCredentialid() != null){
            credentialService.updateCredential(credential);
            model.addAttribute("success", "Successfully updated credential");
        }else {
            if(credentialService.createCredential(credential) > -1) {
                model.addAttribute("success", "Successfully added credential");
            }else {
                //create new
                model.addAttribute("error", "Something went wrong.");
            }
        }

        return "result";
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

        credentialService.deleteCredential(id);
        model.addAttribute("success", "Successfully deleted credential");
        return "result";
    }
}
