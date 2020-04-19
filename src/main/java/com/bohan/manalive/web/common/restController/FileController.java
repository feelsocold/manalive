package com.bohan.manalive.web.common.restController;

import com.bohan.manalive.config.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController {

    private final S3Uploader s3Uploader;

    @PostMapping("/profilePhotoUpload")
    public void profilePhotoUpload(@RequestParam("data") MultipartFile multipartFile) throws IOException{
          s3Uploader.upload(multipartFile, "static");
    }



    @PostMapping("/boardPhotoUpload")
    public void boardPhotoUpload(MultipartFile[] uploadFile) throws IOException {
        log.info("boardPhotoUpload()");
//        s3Uploader.upload(uploadFile, "static");

    }
}