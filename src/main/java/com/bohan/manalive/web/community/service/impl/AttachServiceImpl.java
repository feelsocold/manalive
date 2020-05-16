package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.common.domain.attach.AttachRepository;
import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.common.dto.AttachSaveRequestDto;
import com.bohan.manalive.web.community.service.AttachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttachServiceImpl implements AttachService {

    private final HttpSession httpSession;
    private final AttachRepository attachRepository;
    private final S3Uploader s3Uploader;

    @Override
    public Long saveAttach(AttachSaveRequestDto requestDto) throws Exception {
        return attachRepository.save(requestDto.toEntity()).getAtt_no();
    }

    @Override
    public void deleteAttach(Long superKey, String category) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        List<AttachResponseDto> attachList = attachRepository.getAttachListBySuperKey(superKey, category);
        for(AttachResponseDto dto : attachList) {
            //Attach 테이블에서 삭제
            attachRepository.deleteById(dto.getAtt_no());
            //S3에 업로드된 파일 삭제
            String dateDir = formatter.format(dto.getCreateDate());
            String dirName = category + "/" + dateDir;
            String fileName = dto.getUuid() + "_" + dto.getFilename();
            s3Uploader.deleteFile(dirName, fileName);

        }
    }

    @Override
    public void saveAttachs(String category, Long superkey) throws Exception {
        List<AttachSaveRequestDto> attachList = (List<AttachSaveRequestDto>)httpSession.getAttribute("attachList");

        for(AttachSaveRequestDto attach : attachList){
            attach.setCategory(category);
            attach.setSuperKey(superkey);
            attachRepository.save(attach.toEntity());
        }
    }
}
