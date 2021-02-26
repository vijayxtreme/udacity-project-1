package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CredentialMapper {
    //Read
    @Select("SELECT * FROM credentials")
    List<Credentials> getAllCredentials();

    //Get credentials by user


    //Get credential by user with id

    //Create
    @Insert("INSERT INTO credentials () VALUES ()")
    Integer addCredential(Credentials credentials);

    //Update
    @Update("UPDATE credentials SET WHERE credentialsid = #{}")
    Integer updateCredential(Credentials credentials);

    //Delete
    @Delete("DELETE FROM credentials WHERE credentials = #{}")
    void deleteCredential(Credentials credentials);
}
