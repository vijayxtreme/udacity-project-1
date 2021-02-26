package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

public class FileController {
    private FileService fileService;

    //DI
    FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/file-upload")
    public String fileUpload(File file){
        //get the file from the fileupload (iostream)
        //get the filename, save in file's name
        //save the byte data
        //save the userid
        return "result";
    }

    @DeleteMapping("/deleteFile/{id}")
    public String deleteFile(){
        //get request param, look up in service, then delete if exists
        return "result";
    }
}
