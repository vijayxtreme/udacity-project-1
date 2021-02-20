package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
//maps our SQL inserts to functions in Java we can use in a service
@Mapper
public interface UserMapper {
    //return a list of users
    @Select("SELECT * FROM USERS")
    List<User> getAllUsers();

    //return user by username
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    //insert a new user, give us back the key userid, auto gen id
    @Insert("INSERT INTO users (username, salt, firstname, lastname, password) VALUES (#{username}, #{salt}, #{firstname}, #{lastname}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty="userid")
    int addUser(User user);
}
