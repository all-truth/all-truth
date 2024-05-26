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
    private String url;
    private String name;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;


    @Builder
    private ReviewImage(String name, String url, Review review){
        this.name = name;
        this.review = review;
        this.url = url;
        review.getReviewImages().add(this);
    }
}
