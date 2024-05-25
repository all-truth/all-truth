package com.alltruth.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDTO {
    @Getter
    public static class CommentReq{
        private String content;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class CommentRes{
        private Long id;
        private String content;
        private Long userId;
        private String nickname;
    }
}
