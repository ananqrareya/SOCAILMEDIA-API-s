package com.social.media.Dto;

public class UserSummaryDTO {
    String userName;
    String email ;
    PostDto postDto;

    public UserSummaryDTO(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public UserSummaryDTO(String userName, String email, PostDto postDto) {
        this.userName = userName;
        this.email = email;
        this.postDto = postDto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
