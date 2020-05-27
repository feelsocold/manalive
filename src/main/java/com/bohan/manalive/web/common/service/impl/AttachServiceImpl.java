package com.bohan.manalive.web.common.service.impl;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.web.common.domain.attach.Attach;
import com.bohan.manalive.web.common.domain.attach.AttachRepository;
import com.bohan.manalive.web.common.dto.AttachResponseDto;
import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.service.AttachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AttachServiceImpl implements AttachService {

    private final HttpSession httpSession;
    private final AttachRepository attachRepository;
    private final S3Uploader s3Uploader;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");


    @Transactional
    @Override
    public List<AttachDto> getAttachList(Long superKey, String category) throws Exception {
        return attachRepository.getAttachListBySuperKey(superKey, category);
    }

    @Override
    public Long saveAttach(AttachDto requestDto) throws Exception {
        return attachRepository.save(requestDto.toEntity()).getAtt_no();
    }

    @Override
    public void deleteAttachs(Long superKey, String category) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        List<AttachDto> attachList = attachRepository.getAttachListBySuperKey(superKey, category);
        for(AttachDto dto : attachList) {
            log.info(dto.getFilename() + ",,,," + dto.getSuperKey());
            //Attach 테이블에서 삭제
            attachRepository.deleteById(dto.getAtt_no());
            //S3에 업로드된 파일 삭제
            String dateDir = formatter.format(dto.getCreateDate());
            String dirName = category + "/" + dateDir;
            String fileName = dto.getUuid() + "_" + dto.getFilename() + "." + dto.getExtension();
            s3Uploader.deleteFile(dirName, fileName);

        }
    }

    @Override
    public void saveAttachs(String category, Long superkey) throws Exception {
        List<AttachDto> attachList = (List<AttachDto>)httpSession.getAttribute("attachList");

        for(AttachDto attach : attachList){
            attach.setCategory(category);
            attach.setSuperKey(superkey);
            attachRepository.save(attach.toEntity());
        }
    }

    @Override
    public void deleteAttachOne(Long att_no, String category) throws Exception {
        Attach dto = attachRepository.findById(att_no).get();
        //S3에 업로드된 파일 삭제
        String dateDir = formatter.format(dto.getModifiedDate());
        String dirName = category + "/" + dateDir;
        String fileName = dto.getUuid() + "_" + dto.getFilename() +"." + dto.getExtension();
        s3Uploader.deleteFile(dirName, fileName);

        attachRepository.deleteById(att_no);
    }
}
