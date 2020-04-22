package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.web.community.domain.AttachSaveRequestDto;
import com.bohan.manalive.web.community.service.AttachSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class AttachSessionServiceImpl implements AttachSessionService {

    private final S3Uploader s3Uploader;
    private final HttpSession httpSession;

    @Override
    public void deleteS3Attach(String oper, String category) {
        int delNumber = Integer.parseInt(oper);
        List<AttachSaveRequestDto> attachList = (List<AttachSaveRequestDto>)httpSession.getAttribute("attachList");
        AttachSaveRequestDto requestDto = attachList.get(delNumber);
        String fileName = requestDto.getUuid() + "_" + requestDto.getFilename() + "." + requestDto.getExtension();
        String str = "";

        for(AttachSaveRequestDto attach : attachList) {
            str += attach.getFilename() + ", ";
        }
        log.info("삭제전 : " + str);
        log.info("세션사이즈 : " + attachList.size() + "");

        // 파일첨부 세션에서 삭제
        attachList.remove(delNumber);
        httpSession.setAttribute("attachList", attachList);
        // s3 스토리지에서 삭제
        s3Uploader.deleteFile(category, fileName);
        // 확인
        for(AttachSaveRequestDto attach : attachList) {
            str += attach.getFilename() + ", ";
        }
        log.info("삭제후 : " + str);
        log.info("세션사이즈 : " + attachList.size() + "");
    }
}
