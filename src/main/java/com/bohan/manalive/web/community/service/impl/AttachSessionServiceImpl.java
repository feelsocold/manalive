package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.web.community.domain.AttachSaveRequestDto;
import com.bohan.manalive.web.community.service.AttachSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class AttachSessionServiceImpl implements AttachSessionService {

    private final S3Uploader s3Uploader;
    private final HttpSession httpSession;

    @Override
    public void deleteS3Attach(String oper, String category) {
        List<AttachSaveRequestDto> attachList = (List<AttachSaveRequestDto>)httpSession.getAttribute("attachList");
        log.info("삭제 전 세션사이즈 : " + attachList.size() + "");
        int delNumber = Integer.parseInt(oper);
        AttachSaveRequestDto requestDto = attachList.get(delNumber);
        String fileName = requestDto.getUuid() + "_" + requestDto.getFilename() + "." + requestDto.getExtension();

        // 파일첨부 세션에서 삭제
        attachList.remove(delNumber);
        httpSession.removeAttribute("attachList");
        httpSession.setAttribute("attachList", attachList);
        // s3 스토리지에서 삭제
        s3Uploader.deleteFile(category, fileName);
        // 확인
        for(AttachSaveRequestDto attach : attachList){
            System.out.print(attach.getFilename() + ", ");
        }
        log.info("삭제 후 세션사이즈 : " + attachList.size() + "");
    }

    @Override
    public void updateS3Attach(String oper, String category) {
        List<AttachSaveRequestDto> attachList = (List<AttachSaveRequestDto>)httpSession.getAttribute("attachList");
        log.info( "수정 전 세션 사이즈2 : " + ((List<AttachSaveRequestDto>) httpSession.getAttribute("attachList")).size() + "" );
        log.info("수정 전 세션사이즈 : " + attachList.size() + "");
        for(AttachSaveRequestDto attach : attachList){
            System.out.print(attach.getFilename() + ", ");
        }
        System.out.println("!");
        AttachSaveRequestDto requestDto = attachList.get(Integer.parseInt(oper));
        String fileName = requestDto.getUuid() + "_" + requestDto.getFilename() + "." + requestDto.getExtension();

        // s3 스토리지에서 삭제
        s3Uploader.deleteFile(category, fileName);
        // 파일첨부 세션 수정
        AttachSaveRequestDto newDto = attachList.get(attachList.size()-1);
        attachList.set(Integer.parseInt(oper), newDto);
        attachList.remove(attachList.size()-1);
        httpSession.setAttribute("attachList", attachList);
        // 확인
        for(AttachSaveRequestDto attach : attachList){
            System.out.print(attach.getFilename() + ", ");
        }
        log.info("수정 후 세션사이즈 : " + attachList.size() + "");

    }
}
