package com.jithin.Ecommerce.services;

import com.cloudinary.utils.ObjectUtils;
import com.jithin.Ecommerce.exceptions.FileNullException;
import org.springframework.stereotype.Service;

import com.cloudinary.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
public class FileUploadService {


    private final Cloudinary cloudinary;

    public FileUploadService() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CloudinaryConstants.appName,
                "api_key", CloudinaryConstants.api_key,
                "api_secret", CloudinaryConstants.appSecret));;
    }

    public Map uploadFile(MultipartFile file) {


        try {
            return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf(e.getLocalizedMessage());
            throw new FileNullException("cannot possible to upload file");
        }


    }
}
