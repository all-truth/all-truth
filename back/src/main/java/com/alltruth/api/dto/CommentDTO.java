package com.alltruth.api.dto;

import com.alltruth.api.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

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

    @Getter
    public static class PageCommentRes{
        private List<CommentDTO.CommentRes> data;
        private Long totalElements;
        private Integer totalPages;
        private Integer page;

        public PageCommentRes toCommentResByPage(Page<Comment> pages){
            this.data = pages.getContent().stream()
                    .map((comment)-> CommentRes.builder()
                            .id(comment.getId())
                            .content(comment.getContent())
                            .userId(comment.getUser().getId())
                            .nickname(comment.getUser().getNickname())
                            .build())
                    .toList();

            this.totalPages = pages.getTotalPages();
            this.totalElements = pages.getTotalElements();
            this.page = pages.getNumber() + 1;

            return this;
        }
    }
}
