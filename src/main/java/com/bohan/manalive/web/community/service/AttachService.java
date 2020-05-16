package com.bohan.manalive.web.community.service;

import com.bohan.manalive.web.common.dto.AttachSaveRequestDto;

public interface AttachService {

    public Long saveAttach(AttachSaveRequestDto requestDto) throws Exception;
    public void deleteAttach(Long superKey, String category) throws Exception;
    public void saveAttachs(String category, Long superkey) throws Exception;
}
