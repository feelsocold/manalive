package com.bohan.manalive.config;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.bohan.manalive.web.community.domain.AttachSaveRequestDto;
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
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final HttpSession httpSession;
    private final AmazonS3Client amazonS3Client;


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private void deleteFile(String dirName, String fileName) {
        String key = dirName + "/" + fileName;
        //amazonS3Client.deleteObject(bucket, key);
        amazonS3Client.deleteObject(bucket, "profilePhoto/2020/04/20/0ab2f76f-3873-4793-b6e1-654fce7ff191_nongnongnong.png");
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

    private String getTodayFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String str = sdf.format(date);
        return str;
    }

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        return upload(uploadFile, dirName);
    }

    private void setSessionAttach(String filename, String extension, String uuid, String category) {
        AttachSaveRequestDto attach = new AttachSaveRequestDto(filename, extension, uuid, category);
        httpSession.setAttribute("attachDto", attach);
    }

    private String upload(File uploadFile, String dirName) {
    // 파일명 중복방지용 UUID
        UUID uuid = UUID.randomUUID();
        int pos = uploadFile.getName().lastIndexOf( "." );
        String extenstion= uploadFile.getName().substring( pos + 1 );
        String onlyFilename = uploadFile.getName().substring(0, pos);
        setSessionAttach(onlyFilename, extenstion, uuid.toString(), dirName);

    // 구분폴더이름 + 현재 날짜
        dirName = dirName + "/" + getTodayFolder();

        // getFileList(dirName);
        // deleteFile("not", "hthi");

        String fileName = uuid.toString() + "_"+ uploadFile.getName();
        fileName = dirName + "/" + fileName;
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);



        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
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
