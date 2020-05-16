package com.easydinein.easy_dinein.services;


import com.easydinein.easy_dinein.commands.ImagesCommand;
import com.easydinein.easy_dinein.domain.Images;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Images addImages(ImagesCommand imagesCommand);

    void uploadImage(MultipartFile image, String path, String uid);

    String getImageBase64(String path, String name);
}
