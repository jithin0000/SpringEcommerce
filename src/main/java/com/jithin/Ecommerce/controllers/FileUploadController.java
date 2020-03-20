package com.jithin.Ecommerce.controllers;

import com.jithin.Ecommerce.exceptions.FileNullException;
import com.jithin.Ecommerce.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileUploadController {

    @Autowired
    private FileUploadService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestBody MultipartFile file){

        if (file == null) {
            throw new FileNullException("no file in request");
        }

        Map response = service.uploadFile(file);
        return ResponseEntity.ok(response);
    }


}
