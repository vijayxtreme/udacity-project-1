package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    //DI
    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService){
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
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

    //get credential by id
    public Credentials getCredentialById(String id){
        return this.credentialMapper.getCredentialById(id);
    }

    //create credential
    public int createCredential(Credentials credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);

        return this.credentialMapper.addCredential(credential);
    }

    //update credential
    public void updateCredential(Credentials credential){
        credentialMapper.updateCredential(credential);
    }

    //delete credential
    public void deleteCredential(String credentialid){
        credentialMapper.deleteCredential(credentialid);
    }
}
