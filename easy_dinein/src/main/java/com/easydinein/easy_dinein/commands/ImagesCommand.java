package com.easydinein.easy_dinein.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ImagesCommand {

    private Long id;
    private Long dishId;
    private MultipartFile[] path;
}
