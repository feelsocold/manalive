package com.bohan.manalive.web.community.service;

import com.bohan.manalive.web.community.domain.AttachSaveRequestDto;

public interface AttachService {

    public Long saveAttach(AttachSaveRequestDto requestDto) throws Exception;
    public Long deleteAttach(AttachSaveRequestDto requestDto) throws Exception;
}
