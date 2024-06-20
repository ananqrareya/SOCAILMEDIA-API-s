package com.social.media.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeDto {
    private int id;
    private String userName;

    public LikeDto(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
