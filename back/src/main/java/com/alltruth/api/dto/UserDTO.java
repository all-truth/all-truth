package com.alltruth.api.dto;

import lombok.*;

public class UserDTO {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class UserJoinReq{
        private String loginId;
        private String password;
        private String passwordConfirm;
        private String nickname;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class UserJoinRes{
        private String message;

    }

    @NoArgsConstructor
    @Getter
    public static class UserInfoRes{
        private Long id;
        private String nickname;

        @Builder
        private UserInfoRes(Long id, String nickname){
            this.id = id;
            this.nickname = nickname;
        }
    }
}
