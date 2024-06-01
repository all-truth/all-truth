package com.alltruth.api.dto;

import com.alltruth.api.entity.User;
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
        private String image = "";

        @Builder
        private UserInfoRes(Long id, String nickname, String image){
            this.id = id;
            this.nickname = nickname;
            this.image = image;
        }

        public UserInfoRes toUserInfoResByUser(User user){
            this.id = user.getId();
            this.nickname = user.getNickname();
            this.image = user.getImage() != null ? user.getImage().getUrl() : "";

            return this;
        }
    }
}
