package com.udacity.jwdnd.course1.cloudstorage.controllers;

//referenced Mykong.com example (for handling large requests over exceeded size)
//https://mkyong.com/spring-boot/spring-boot-file-upload-example/

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MultipartException.class)
    public String handleErrorWithUpload(MultipartException e, Model model){
        model.addAttribute("customError","Sorry, there was an issue uploading your file. File size limit is 10MB");
        return "error";
    }


}
