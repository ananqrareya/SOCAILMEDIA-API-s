package com.social.media.Dto;

import com.social.media.Entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class UserPostDTO {
    private String userName;
    private List<PostDto> posts;


    public UserPostDTO(String userName, List<PostDto> posts) {
        this.userName = userName;
        this.posts = posts;
    }
}
