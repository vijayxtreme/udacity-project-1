package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    //DI
    public FileService(FileMapper fileMapper){
        this.fileMapper = fileMapper;
    }

    @PostConstruct()
    public void postConstruct(){
        System.out.println("Files: ");
    //    System.out.println(this.fileMapper.getFiles());
    }

    //get all files
    public List<File> getAllFiles(){
        return this.fileMapper.getFiles();
    }

    //get all files belong to current user
    public List<File> getAllFilesBelongToUser(User user){
        String userid = String.valueOf(user.getUserid());
        return this.fileMapper.getFilesByUserId(userid);
    }

    //get File by id
    public File getFileById(String fileid){
        return this.fileMapper.getFileById(fileid);
    }

    //createFile
    public int createFile(File file){
        return this.fileMapper.createFile(file);
    }

    //updateFile
    public void updateFile(File file){
        fileMapper.updateFile(file);
    }

    //deleteFile
    public void deleteFile(String fileid){
        fileMapper.deleteFile(fileid);
    }

}
