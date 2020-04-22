package com.bohan.manalive.web.community.service.impl;

import com.bohan.manalive.domain.attach.AttachRepository;
import com.bohan.manalive.web.community.domain.AttachSaveRequestDto;
import com.bohan.manalive.web.community.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AttachServiceImpl implements AttachService {

    private final AttachRepository attachRepository;

    public Long saveAttach(AttachSaveRequestDto requestDto) throws Exception {
        return attachRepository.save(requestDto.toEntity()).getAtt_no();
    }

    @Override
    public Long deleteAttach(AttachSaveRequestDto requestDto) throws Exception {
        return null;
    }


}
