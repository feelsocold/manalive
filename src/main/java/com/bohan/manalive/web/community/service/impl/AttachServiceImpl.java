package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.domain.attach.AttachRepository;
import com.bohan.manalive.web.community.dto.AttachSaveRequestDto;
import com.bohan.manalive.web.community.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AttachServiceImpl implements AttachService {

    private final HttpSession httpSession;
    private final AttachRepository attachRepository;

    @Override
    public Long saveAttach(AttachSaveRequestDto requestDto) throws Exception {
        return attachRepository.save(requestDto.toEntity()).getAtt_no();
    }

    @Override
    public Long deleteAttach(AttachSaveRequestDto requestDto) throws Exception {
        return null;
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
