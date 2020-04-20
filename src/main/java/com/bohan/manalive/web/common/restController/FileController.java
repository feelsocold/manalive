package com.bohan.manalive.web.common.restController;

import com.bohan.manalive.config.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController implements Serializable {

    private final S3Uploader s3Uploader;
    @PostMapping("/s3Upload")
    public void s3Upload(MultipartFile[] multipartFile,
                @RequestParam("category") String category) throws IOException{
        for(MultipartFile file : multipartFile){
            s3Uploader.upload(file, category);
        }
    }


}
