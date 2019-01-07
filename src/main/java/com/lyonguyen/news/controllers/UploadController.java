package com.lyonguyen.news.controllers;

import com.lyonguyen.news.models.CKEditorUploadResponse;
import com.lyonguyen.news.services.CloudStorageService;
import com.lyonguyen.news.services.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UploadController {

    @Autowired
    private CloudStorageService cloudStorageService;

    @Autowired
    private GoogleDriveService googleDriveService;

    @PostMapping("/api/upload")
    public CKEditorUploadResponse upload(@RequestParam("upload") MultipartFile file) throws IOException {

        Resource resource = googleDriveService.write(file.getOriginalFilename(), file.getInputStream());

        return new CKEditorUploadResponse(1, file.getOriginalFilename(), resource.getURL().toString());
    }
}
