package com.bohan.manalive.web.common.restController;

import com.bohan.manalive.config.S3Uploader;
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
    public void s3Delete(@RequestParam(value="oper") String oper, @RequestParam(value="att_no", required=false) Long att_no,
                            @RequestParam("category") String category) throws IOException{
        log.info("s3Delete");
        // 게시판 글 작성 중, 회원가입 프로필사진 변경시
        if(httpSession.getAttribute("attachList") != null ) {
            attachSessionService.deleteS3Attach(oper, category);
        }
        // 게시글 수정 시
        else{
            log.info(att_no + "");
            //attachService.deleteAttach(att_no, );
        }

    }

    @PostMapping("/s3Update")
    public List<String> s3Update(MultipartFile[] multipartFile,
                         @RequestParam("oper") String oper,         // 사진 넘버
                         @RequestParam("category") String category) throws IOException{
        log.info("s3Update");
        List<String> fileList = s3Uploader.upload(multipartFile, category);
        attachSessionService.updateS3Attach(oper, category);
        //List<AttachSaveRequestDto> attachList = (List<AttachSaveRequestDto>)httpSession.getAttribute("attachList");

        return fileList;
    }


}
