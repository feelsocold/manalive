package com.bohan.manalive.web.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardUserDetailDto implements Serializable {

    private String nickname;
    private String photo;

}
