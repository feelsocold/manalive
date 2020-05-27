package com.bohan.manalive.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.bohan.manalive.web.common.dto.AttachDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final HttpSession httpSession;
    private final AmazonS3Client amazonS3Client;
    List<AttachDto> attachList = new ArrayList<>();

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void deleteFile(String dirName, String fileName) {
        log.info(dirName +  "에서 " + fileName + "이 삭제되었습니다.");

        //String key = dirName + "/" + getTodayFolder() + "/" + fileName;
        String key = dirName + "/" + fileName;
        amazonS3Client.deleteObject(bucket, key);
        //amazonS3Client.deleteObject(bucket, "profilePhoto/2020/04/20/0ab2f76f-3873-4793-b6e1-654fce7ff191_nongnongnong.png");
    }

    private void getFileList(String folderName) {
        ListObjectsRequest listObject = new ListObjectsRequest();
        listObject.setBucketName(bucket);
        listObject.setPrefix(folderName);
        ObjectListing objects = amazonS3Client.listObjects(listObject);

        do {
            objects = amazonS3Client.listObjects(listObject);
            for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
                //log.info(objectSummary.getKey());
                System.out.println(objectSummary.toString());
            }
        } while (objects.isTruncated());
    }

    public String getTodayFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str;
    }

    private void setSessionAttach(String filename, String extension, String uuid, String category, String url) {

        if(httpSession.getAttribute("attachList") != null ) {
            attachList = (List<AttachDto>) httpSession.getAttribute("attachList");
        }
        AttachDto attach = new AttachDto(filename, extension, uuid, category, url);
        attachList.add(attach);
        httpSession.setAttribute("attachList", attachList);
    }

    public List<String> upload(MultipartFile[] multipartFile, String dirName) throws IOException {
        File[] uploadFile = new File[multipartFile.length];

        for(int i = 0; i < multipartFile.length; i++) {
            uploadFile[i] = convert(multipartFile[i])
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        }
        return upload(uploadFile, dirName);
    }

    private List<String> upload(File[] uploadFiles, String dirName) {
        List<String> uploadImageUrls = new ArrayList<>();
        String category = dirName;
        // 구분폴더 이름 + 현재 날짜
        dirName = dirName + "/" + getTodayFolder();

        for(File uploadFile : uploadFiles) {
        // 파일명 중복방지용 UUID
            UUID uuid = UUID.randomUUID();
        // 확장자/파일명 구분
            int pos = uploadFile.getName().lastIndexOf(".");
            String extenstion = uploadFile.getName().substring(pos + 1);
            String onlyFilename = uploadFile.getName().substring(0, pos);

            // getFileList(dirName);

            String fileName = uuid.toString() + "_" + uploadFile.getName();
            fileName = dirName + "/" + fileName;
            String uploadImageUrl = putS3(uploadFile, fileName);
            uploadImageUrls.add(uploadImageUrl);
            removeNewFile(uploadFile);
            // 세션 관리
            if(httpSession.getAttribute("attachList") == null){attachList.clear();}
            setSessionAttach(onlyFilename, extenstion, uuid.toString(), category, uploadImageUrl);
        }
        return uploadImageUrls;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            //log.info("로컬파일이 삭제되었습니다.");
        } else {
            //log.info("로컬파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
