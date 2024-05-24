package com.alltruth.api.dto;

import com.alltruth.api.entity.Review;
import lombok.*;

import java.util.List;

public class ReviewDTO {
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class ReviewReq{
        private String title;
        private String content;
        private String storeName;
        private String region;
    }

    @NoArgsConstructor
    @Getter
    public static class ReviewRes{
        private Long id;
        private String title;
        private String content;
        private List<ReviewImageDTO.ReviewImageRes> images;
        private String receiptImage;
        private String storeName;
        private String region;

        @Builder
        private ReviewRes(Long id, String title, String content, List<ReviewImageDTO.ReviewImageRes> images,
                        String receiptImage, String storeName, String region){
            this.id = id;
            this.title = title;
            this.content = content;
            this.images = images;
            this.region = region;
            this.storeName = storeName;
            this.receiptImage = receiptImage;
        }


        public ReviewRes toEntityByReview(Review review){
            this.images = review.getReviewImages().stream().map((img) -> ReviewImageDTO.ReviewImageRes
                    .builder()
                    .url("http://localhost:8080/review/img/"+ img.getPath())
                    .name(img.getPath())
                    .id(img.getId())
                    .build()
            ).toList();

            this.id = review.getId();
            this.title = review.getTitle();
            this.content = review.getContent();
            this.region = review.getRegion();
            this.storeName = review.getStoreName();
            this.receiptImage = "http://localhost:8080/review/img/" + review.getReceiptImage().getPath();

            return this;
        }
    }
}
