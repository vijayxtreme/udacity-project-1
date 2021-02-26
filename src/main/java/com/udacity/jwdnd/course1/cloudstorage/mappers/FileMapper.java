package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM files")
    List<File> getFiles();

    //Get list of files by user
    @Select("SELECT * FROM files WHERE userid = #{userid}")
    List<File> getFilesByUserId(String userid);

    //Get specific file by fileid
    @Select("SELECT * FROM files WHERE fileId = #{fileid}")
    File getFileById(String fileid);

    //Insert
    @Insert("INSERT INTO files (name, url, filedata, userid) VALUES (#{name}, #{url}, #{filedata}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int createFile(File file);

    //Update
    @Update("UPDATE files WHERE name=#{name}, url=#{url}, filedata=#{filedata} WHERE userid=#{userid}")
    void updateFile(File file);

    //Delete
    @Delete("DELETE FROM files WHERE userid= #{fileid}")
    void deleteFile(String fileid);
}
