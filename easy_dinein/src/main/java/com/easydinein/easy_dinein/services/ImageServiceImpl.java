package com.easydinein.easy_dinein.services;

import com.easydinein.easy_dinein.commands.DishCommand;
import com.easydinein.easy_dinein.commands.ImagesCommand;
import com.easydinein.easy_dinein.converters.ImagesCommandToImages;
import com.easydinein.easy_dinein.converters.ImagesToImagesCommand;
import com.easydinein.easy_dinein.domain.Images;
import com.easydinein.easy_dinein.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ImagesCommandToImages imageCommandConverter;
    @Autowired
    private ImagesToImagesCommand imageConverter;


    private static String upload_dir = "D:/uploadimages/";

    public Images addImages(ImagesCommand imagesCommand){

       DishCommand dishCommand = new DishCommand();
      /*
       ArrayList<String> fileNames = null;

       if(dishCommand.getImages().size()>0) {
           fileNames = new ArrayList<String>();
           for(MultipartFile file:imagesCommand.getPath()) {
               if (file.isEmpty()) {
                   return "Please select a file to upload";
               }
               try {
                   file.transferTo(new File(upload_dir + file.getOriginalFilename()));
                   fileNames.add(upload_dir +file.getOriginalFilename());

               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }  */

        return null;

   }

    @Override
    public void uploadImage(MultipartFile image, String path, String uid) {
        try {
            String imageName = image.getOriginalFilename();
            imageName = uid + "-" + imageName.replaceAll("\\s+","-") ;
            image.transferTo(new File(path + imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getImageBase64(String path, String name) {
        String imageBase64 = null;
        try {
            File file = new File(path+name);
            imageBase64 = new String(Base64.getEncoder().encode(Files.readAllBytes(file.toPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBase64;
    }

}
