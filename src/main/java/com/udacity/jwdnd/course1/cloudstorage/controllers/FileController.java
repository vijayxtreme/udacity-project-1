package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.apache.coyote.Response;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping
public class FileController {
    private FileService fileService;

    //DI
    FileController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping("/file/{id}")
    @ExceptionHandler(FileNotFoundException.class)
    public void getFile(HttpServletResponse response, @PathVariable String id, Model model) throws IOException{
        
        File file = fileService.getFileById(id);

        if(file == null){
            model.addAttribute("error", "Error, file not in system");
            response.sendRedirect("error");
        }


        //Using suggestion from Udacity mentor
        String mimetype = URLConnection.guessContentTypeFromName(file.getFilename());
        if(mimetype == null){
            //to download files that aren't supported by browser inline
            mimetype = "application/octet-stream";
        }
        response.setContentType(mimetype);
        response.setContentLength((int) file.getFiledata().length);
        response.setContentType(file.getContenttype());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + file.getFilename());

        InputStream inputStream = new ByteArrayInputStream(file.getFiledata());

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @PostMapping("/file-upload")
    public String fileUpload(@RequestParam("fileUpload") MultipartFile fileUpload, File file, Model model) throws IOException {
        //check against uploading empty files
        if(fileUpload.isEmpty()){
            model.addAttribute("error", "You did not upload a file, or file was corrupt. Please check and try again.");
            return "result";
        }

        //Using globalexceptionhandler to handle uploads over 10mb

        //check against uploading files with name already in DB.
        String filename = fileUpload.getOriginalFilename();
        String userid = String.valueOf(file.getUserid());

        if(fileService.getFileByNameByUser(filename, userid) != null){
            model.addAttribute("error", "File with same name already in system.  Please rename the file");
            return "result";
        }

        try {
            file.setFilename(fileUpload.getOriginalFilename());
            file.setContenttype(fileUpload.getContentType());
            file.setFilesize(String.valueOf(fileUpload.getSize()));
            file.setFiledata(fileUpload.getBytes());
            fileService.createFile(file);

            model.addAttribute("success", "Successfully created file");
        }catch(IOException e){
            model.addAttribute("error", "Error creating the file.");
        }finally {
            return "result";
        }

    }


    @GetMapping("/deleteFile/{id}")
    public String deleteFile(@PathVariable String id, Model model){
        if(id == ""){
            model.addAttribute("error", "Error with trying to delete the file.  Please try again later.");
            return "result";
        }
        try {
            //get request param, look up in service, then delete if exists
            fileService.deleteFile(id);
            model.addAttribute("success", "Successfully deleted file.");
        }catch(Exception e){
            model.addAttribute("error", "There was an issue trying to delete. Please try later");
        }finally {
            return "result";
        }
    }
}
