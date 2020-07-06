package com.bohan.manalive.web.common.restController;

import com.bohan.manalive.web.common.dto.AttachDto;
import com.bohan.manalive.web.common.service.AttachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/attach")
@RestController
public class AttachRestController {

    private final AttachService attachService;

    @PostMapping("/getMainPhoto")
    public String getMainPhoto(@RequestParam("seq") Long seq,
                                        @RequestParam("category") String category) throws Exception {
        List<AttachDto> attachList = attachService.getAttachList(seq, category);

        return attachList.get(0).getUrl();
    }

}
