package com.ashish.planservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map upload(MultipartFile file, String folder);

    Map<String, Object> uploadVideo(MultipartFile file , String folder);
}
