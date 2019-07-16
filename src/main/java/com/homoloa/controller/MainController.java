package com.homoloa.controller;

import com.homoloa.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainController {

    @Autowired
    private ParseService parseService;

    @PostMapping("/upload")
    public ResponseEntity<InputStreamResource> parseUploadedFile(@RequestParam("file") MultipartFile file){

       if(file != null){
           return parseService.parseFile(file);
       }
       return null;
    }
}