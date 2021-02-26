package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    //DI
    public CredentialService(CredentialMapper credentialMapper){
        this.credentialMapper = credentialMapper;
    }

    //post construct
    @PostConstruct()
    public void postConstruct(){
        System.out.println("Credentials");
    }

    //get all credentials
    public List<Credentials> getAllCredentials(){
        return this.credentialMapper.getAllCredentials();
    }

    //get all credentials by userid
    public List<Credentials> getAllCredentialsBelongToUser(User user){
        String userid = String.valueOf(user.getUserid());
        return this.credentialMapper.getCredentialByUserId(userid);
    }

    //create credential
    public int createCredential(Credentials credential){
        return this.credentialMapper.addCredential(credential);
    }

    //update credential
    public void updateCredential(Credentials credential){
        credentialMapper.updateCredential(credential);
    }

    //delete credential
    public void deleteCredential(Credentials credential){
        credentialMapper.deleteCredential(credential);
    }
}
