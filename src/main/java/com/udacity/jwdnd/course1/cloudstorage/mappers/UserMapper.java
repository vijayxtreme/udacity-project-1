package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS")
    List<User> getAllUsers();

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO users (username, salt, firstname, lastname, password) VALUES (#{username}, #{salt}, #{firstname}, #{lastname}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty="userid")
    int addUser(User user);
}
