package com.alltruth.api.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_image_id")
    private Long id;
    private String path;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;


    @Builder
    private ReviewImage(String path, Review review){
        this.path = path;
        this.review = review;
        review.getReviewImages().add(this);
    }
}
