package com.social.media.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.social.media.Entity.Like;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostDto {
    private int id;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp createdAt;
   private int likes;
   private List<LikeDto>userWhoLiked;
   private List<CommentDto>userWhoComment;

    public PostDto(int id,String content) {
        this.id=id;
        this.content = content;
    }


    public PostDto(int id, String content, int likes, List<LikeDto> userWhoLiked) {
        this.id = id;
        this.content = content;
        this.likes = likes;
        this.userWhoLiked = userWhoLiked;
    }

    public PostDto(int id, String content, Timestamp createdAt, int likes, List<LikeDto> userWhoLiked) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.likes = likes;
        this.userWhoLiked = userWhoLiked;
    }

    public PostDto(int id, String content, Timestamp createdAt, int likes, List<LikeDto> userWhoLiked, List<CommentDto> userWhoComment) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.likes = likes;
        this.userWhoLiked = userWhoLiked;
        this.userWhoComment = userWhoComment;
    }
}
