package com.jithin.Ecommerce.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileUploadServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private FileUploadService SUT;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void uploadFile() throws IOException {

        // TODO: 20/03/20 need to implement this

    }

    private Map response() {
        Map map = new HashMap();
        map.put("original_filename", "file");
        return map;
    }
}