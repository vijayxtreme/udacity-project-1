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

    @Select("SELECT * FROM files WHERE filename = #{filename} AND userid= #{userid}")
    File getFileByNameByUser(String filename, String userid);

    //Insert
    @Insert("INSERT INTO files (filename, contenttype, filesize, filedata, userid) VALUES (#{filename}, #{contenttype}, #{filesize}, #{filedata}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int createFile(File file);

    //Update
    @Update("UPDATE files WHERE filename=#{filename}, contenttype=#{contenttype}, filesize=#{filesize}, filedata=${filedata} WHERE userid=#{userid}")
    void updateFile(File file);

    //Delete
    @Delete("DELETE FROM files WHERE fileid= #{fileid}")
    void deleteFile(String fileid);
}
