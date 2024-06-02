package com.alltruth.api.dto;

import com.alltruth.api.entity.Review;
import lombok.*;
import org.springframework.data.domain.Page;

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
        private Long userId;

        @Builder
        private ReviewRes(Long id, String title, String content, List<ReviewImageDTO.ReviewImageRes> images,
                        String receiptImage, String storeName, String region, Long userId){
            this.id = id;
            this.title = title;
            this.content = content;
            this.images = images;
            this.region = region;
            this.storeName = storeName;
            this.receiptImage = receiptImage;
            this.userId = userId;
        }


        public ReviewRes toReviewResByReview(Review review){
            this.images = review.getReviewImages().stream().map((img) -> ReviewImageDTO.ReviewImageRes
                    .builder()
                    .url(img.getUrl())
                    .name(img.getName())
                    .id(img.getId())
                    .build()
            ).toList();

            this.id = review.getId();
            this.title = review.getTitle();
            this.content = review.getContent();
            this.region = review.getRegion();
            this.storeName = review.getStoreName();
            this.receiptImage = review.getReceiptImage() != null ?  review.getReceiptImage().getUrl() : "";
            this.userId = review.getUser().getId();

            return this;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class PageReviewRes{
        private List<ReviewRes> data;
        private Long totalElements;
        private Integer totalPages;
        private Integer page;




        public PageReviewRes toReviewResByReview(Page<Review> pages){
            this.data = pages.getContent().stream()
                    .map((review) -> new ReviewRes().toReviewResByReview(review))
                    .toList();

            this.totalPages = pages.getTotalPages();
            this.totalElements = pages.getTotalElements();
            this.page = pages.getNumber() + 1;

            return this;
        }
    }
}
