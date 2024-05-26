package com.alltruth.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewImageDTO {
    @NoArgsConstructor
    @Getter
    public static class ReviewImageRes{
            private Long id;
            private String name;
            private String url;

            @Builder
            private ReviewImageRes(Long id, String name, String url){
                this.id = id;
                this.name = name;
                this.url = url;
            }
    }
}
