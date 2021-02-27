package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    //Get all credentials
    @Select("SELECT * FROM credentials")
    List<Credentials> getAllCredentials();

    //Get credentials by userid
    @Select("SELECT * FROM credentials WHERE userid=#{userid}")
    List<Credentials> getCredentialByUserId(String userid);

    //Get specific credential by credentialid
    @Select("SELECT * FROM credentials WHERE credentialid= #{credentialid}")
    Credentials getCredentialById(String credentialid);

    //Create
    @Insert("INSERT INTO credentials (url, username, password, key, userid) VALUES (#{url}, #{username}, #{password}, #{key}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int addCredential(Credentials credential);

    //Update
    @Update("UPDATE credentials SET url=#{url}, username=#{username}, password=#{password}, key=#{key} WHERE credentialid = #{credentialid}")
    void updateCredential(Credentials credential);

    //Delete
    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialid}")
    void deleteCredential(String credentialid);
}
