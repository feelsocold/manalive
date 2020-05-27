package com.bohan.manalive.web.community.restController;

import com.bohan.manalive.config.S3Uploader;
import com.bohan.manalive.config.oauth.LoginUser;
import com.bohan.manalive.config.oauth.dto.SessionUser;
import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.service.AttachService;
import com.bohan.manalive.web.common.service.AttachSessionService;
import com.bohan.manalive.web.community.dto.BoardLikeRequestDto;
import com.bohan.manalive.web.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardRestController  {
    private final HttpSession httpSession;
    private final S3Uploader s3Uploader;
    private final BoardService boardService;
    private final AttachService attachService;
    private final AttachSessionService attachSessionService;

    @PostMapping("/boardDetail")
    public HashMap<String, Object> getBoardDetail(@RequestParam String seq) throws Exception {
        log.info(seq);
        return boardService.boardDetail(Long.parseLong(seq));
    }

    @DeleteMapping("/boardDelete/{b_seq}")
    public void deleteBoard(@PathVariable("b_seq") Long b_seq) throws Exception {
        boardService.deleteBoard(b_seq);
        //Attach 테이블에서 첨부파일 삭제 + S3에 업로드된 파일 삭제
        attachService.deleteAttachs(b_seq, "boardPhoto");
    }

    @PostMapping("/boardLike")
    public void boardDoLike(@RequestParam("b_seq") Long b_seq, @LoginUser SessionUser user) throws Exception {
        BoardLikeRequestDto dto = new BoardLikeRequestDto();
        dto.setB_seq(b_seq);
        dto.setEmail(user.getEmail());
        boardService.doLikeBoard(dto);
    }

    @PostMapping("/boardUnLike")
    public void boardDoUnLike(@RequestParam("b_seq") Long b_seq, @LoginUser SessionUser user) throws Exception {
        BoardLikeRequestDto dto = new BoardLikeRequestDto();
        dto.setB_seq(b_seq);
        dto.setEmail(user.getEmail());
        boardService.doLikeBoard(dto);
    }

    @PostMapping("/boardLikeDiscern")
    public boolean boardLikeDiscern(@RequestParam("b_seq") Long b_seq, @LoginUser SessionUser user) throws Exception{
        BoardLikeRequestDto dto = new BoardLikeRequestDto();
        dto.setB_seq(b_seq);
        dto.setEmail(user.getEmail());
        return boardService.discernLikeBoard(dto);
    }

    @PostMapping("/modifyBoardAttachUpdate")
    public List<String> modifyBoardAttachUpdate(MultipartFile[] multipartFile,
                                        @RequestParam("oper") int oper,         // 세션사진 index
                                        @RequestParam("att_no") Long att_no,
                                        @RequestParam(value="b_seq", required=false) Long b_seq,
                                        @RequestParam("category") String category) throws Exception{
        log.info("ATT_NO : " + att_no);
        List<String> fileList = s3Uploader.upload(multipartFile, category);
        List<AttachDto> attachList = (List<AttachDto>)httpSession.getAttribute("attachList");

        // 파일첨부 세션 수정
        AttachDto newDto = attachList.get(attachList.size()-1);
        newDto.setSuperKey(b_seq);
        newDto.setAtt_no(att_no);
        attachList.set(oper, newDto);
        attachList.remove(attachList.size()-1);
        httpSession.setAttribute("attachList", attachList);

        log.info("BOARD수정 첨부파일 수정 후 세션리스트 사이즈 : " + attachList.size());
        List<AttachDto> attachList2 = (List<AttachDto>)httpSession.getAttribute("attachList");
        for (AttachDto dto : attachList2){
            log.info(dto.getAtt_no() + ": " + dto.getFilename() + ", " + dto.getSuperKey());
        }
        return fileList;
    }

    @PostMapping("/modifyBoardAttachDelete")
    public void modifyBoardAttachDelete(@RequestParam(value="oper") int oper, @RequestParam(value="att_no") Long att_no,
                                  @RequestParam("category") String category) throws Exception{

        //attachSessionService.deleteS3Attach(oper, category);
        List<AttachDto> attachList = (List<AttachDto>)httpSession.getAttribute("attachList");
        attachList.get(oper).setFilename("");
        httpSession.setAttribute("attachList", attachList);

        log.info("BOARD수정 삭제 후 세션리스트 사이즈 : " + attachList.size());
    }

    @PostMapping("/modifyBoardAttachUpload")
    public List<String> modifyBoardAttachUpload(MultipartFile[] multipartFile, @RequestParam(value="b_seq", required=false) Long b_seq,
                                        @RequestParam("category") String category) throws Exception{
        log.info("modifyBoardAttachUpload");
        List<String> fileList = s3Uploader.upload(multipartFile, category);
        List<AttachDto> attachList = (List<AttachDto>)httpSession.getAttribute("attachList");
        log.info("BOARD수정 업로드 후 세션리스트 사이즈 : " + attachList.size());

        for(AttachDto dto : attachList){
//            if(dto.getSuperKey() == null){
//                dto.setSuperKey(b_seq);
//            }
            log.info(dto.getAtt_no() + ":" + dto.getFilename() + ", " + dto.getSuperKey() + ", " + dto.getUrl());
        }

        return fileList;
    }
}
