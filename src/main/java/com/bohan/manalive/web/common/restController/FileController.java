package com.bohan.manalive.web.common.restController;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.common.service.AttachSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class FileController implements Serializable {

    private final S3Uploader s3Uploader;
    private final AttachService attachService;
    private final AttachSessionService attachSessionService;
    private final HttpSession httpSession;

    @PostMapping("/s3Upload")
    public List<String> s3Upload(MultipartFile[] multipartFile,
                                 @RequestParam("category") String category) throws IOException{
        List<String> fileList = s3Uploader.upload(multipartFile, category);
        return fileList;
    }

    @PostMapping("/s3Delete")
    public void s3Delete(@RequestParam(value="oper") int oper,
                            @RequestParam("category") String category) throws Exception{
        log.info("s3Delete");
        attachSessionService.deleteS3Attach(oper, category);

        // s3 스토리지에서 삭제
        List<AttachDto> attachList = (List<AttachDto>)httpSession.getAttribute("attachList");
        AttachDto requestDto = attachList.get(oper);
        String fileName = requestDto.getUuid() + "_" + requestDto.getFilename() + "." + requestDto.getExtension();
        String dirName = category + "/" + s3Uploader.getTodayFolder();
        s3Uploader.deleteFile(dirName, fileName);

    }

    @PostMapping("/s3Update")
    public List<String> s3Update(MultipartFile[] multipartFile,
                         @RequestParam("oper") int oper,         // 사진 넘버
                         @RequestParam("category") String category) throws IOException{
        log.info("s3Update");
        List<String> fileList = s3Uploader.upload(multipartFile, category);
        attachSessionService.updateS3Attach(oper, category);

        return fileList;
    }


}
