package com.social.media.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private String comment;
    private String userName;

    public CommentDto(String comment, String userName) {
        this.comment = comment;
        this.userName = userName;
    }
}
