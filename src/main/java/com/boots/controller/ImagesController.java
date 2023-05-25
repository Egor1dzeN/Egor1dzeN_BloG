package com.boots.controller;

import com.boots.entity.Images;
import com.boots.repository.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@RestController
public class ImagesController {

    @Autowired
    ImagesRepository imagesRepository;

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImagesById(@PathVariable Long id) {
            Images images = imagesRepository.findById(id).orElse(null);
            System.out.println(id);

            return ResponseEntity.ok()
                    .header("file_name", images.getOriginalFileName())
                    .contentType(MediaType.valueOf(images.getContentType()))
                    .contentLength(images.getSize())
                    .body(new InputStreamResource(new ByteArrayInputStream(images.getBytes())));




    }
}
