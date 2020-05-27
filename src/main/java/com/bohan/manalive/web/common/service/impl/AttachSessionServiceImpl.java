package com.bohan.manalive.web.common.service.impl;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.service.AttachSessionService;
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
    public void deleteS3Attach(int oper, String category) {
        List<AttachDto> attachList = (List<AttachDto>)httpSession.getAttribute("attachList");
        log.info("삭제 전 세션사이즈 : " + attachList.size() + "");
        //int delNumber = Integer.parseInt(oper);
        AttachDto requestDto = attachList.get(oper);

        // 파일첨부 세션에서 삭제
        attachList.remove(oper);
        httpSession.removeAttribute("attachList");
        httpSession.setAttribute("attachList", attachList);

//        // s3 스토리지에서 삭제
//        String fileName = requestDto.getUuid() + "_" + requestDto.getFilename() + "." + requestDto.getExtension();
//        String dirName = category + "/" + s3Uploader.getTodayFolder();
//        s3Uploader.deleteFile(dirName, fileName);
//        // 확인
//        for(AttachDto attach : attachList){
//            System.out.print(attach.getFilename() + ", ");
//        }
        log.info("삭제 후 세션사이즈 : " + attachList.size() + "");
    }

    @Override
    public void updateS3Attach(int oper, String category) {
        List<AttachDto> attachList = (List<AttachDto>)httpSession.getAttribute("attachList");
        AttachDto requestDto = attachList.get(oper);
        String fileName = requestDto.getUuid() + "_" + requestDto.getFilename() + "." + requestDto.getExtension();

        // s3 스토리지에서 삭제
        s3Uploader.deleteFile(category, fileName);
        // 파일첨부 세션 수정
        AttachDto newDto = attachList.get(attachList.size()-1);
        attachList.set(oper, newDto);
        attachList.remove(attachList.size()-1);
        httpSession.setAttribute("attachList", attachList);
        // 확인
        for(AttachDto attach : attachList){
            System.out.print(attach.getFilename() + ", ");
        }
        log.info("수정 후 세션사이즈 : " + attachList.size() + "");

    }
}
