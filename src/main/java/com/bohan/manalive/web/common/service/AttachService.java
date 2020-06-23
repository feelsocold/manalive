package com.bohan.manalive.web.common.service;

        import com.bohan.manalive.web.common.dto.AttachResponseDto;
        import com.bohan.manalive.web.common.dto.AttachDto;

        import java.util.List;

public interface AttachService {

    public List<AttachDto> getAttachList(Long superKey, String category) throws Exception;
    public Long saveAttach(AttachDto requestDto) throws Exception;
    public void deleteAttachs(Long superKey, String category) throws Exception;
    public void saveAttachs(Long superkey) throws Exception;
    public void deleteAttachOne(Long att_no, String category) throws Exception;

}
