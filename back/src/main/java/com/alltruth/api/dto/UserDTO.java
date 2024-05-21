package com.alltruth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static public class UserJoinReq{
        private String loginId;
        private String password;
        private String passwordConfirm;
        private String nickName;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static public class UserJoinRes{
        private String message;

    }
}
