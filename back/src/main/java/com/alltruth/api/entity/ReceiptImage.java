package com.alltruth.api.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class ReceiptImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_image_id")
    private Long id;

    private String name;
    private String url;

    @ManyToOne
    @JoinColumn(name = "review_id")
    Review review;

    @Builder
    private ReceiptImage(String url, Review review, String name){
        this.url = url;
        this.review = review;
        this.name = name;
        review.setReceiptImage(this);
    }
}
