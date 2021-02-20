package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

//userService for getting and setting users (business logic)
@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    //DI 
    public UserService(UserMapper userMapper, HashService hashService){
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    //is user available?
    public boolean isUsernameAvailable(String username){
        return userMapper.getUser(username) == null;
    }

    //give me all the users
    public List<User> getAllUsers(){
        return userMapper.getAllUsers();
    }

    //get a user
    public User getUser(String username){
        return userMapper.getUser(username);
    }

    //create a user - but first generate a salt, set it on received user and create/set hashedPassword
    public int createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        user.setSalt(encodedSalt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        user.setPassword(hashedPassword);

        return userMapper.addUser(user);
    }
}
