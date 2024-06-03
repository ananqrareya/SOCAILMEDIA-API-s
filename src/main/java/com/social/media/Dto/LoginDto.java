    package com.social.media.Dto;

    import lombok.*;

    @Data
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public class LoginDto {
        private String userName;
        private String pass;
    }
