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

    private String path;

    @ManyToOne
    @JoinColumn(name = "review_id")
    Review review;

    @Builder
    private ReceiptImage(String path, Review review){
        this.path = path;
        this.review = review;
        review.setReceiptImage(this);
    }
}
