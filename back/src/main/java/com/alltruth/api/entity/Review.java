package com.alltruth.api.entity;

import com.alltruth.api.dto.ReviewDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;
    private String title;
    @Lob
    private String content;
    private String storeName;
    private String region;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "review", orphanRemoval = true)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "review", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "review", orphanRemoval = true)
    private ReceiptImage receiptImage;

    @Builder
    private Review(String title, String content, String storeName,
                   String region, User user){
        this.title = title;
        this.content = content;
        this.user = user;
        this.storeName = storeName;
        this.region = region;


        user.getReviewList().add(this);

    }

    public void update(String tite, String content, String storeName, String region){
        this.title = title;
        this.content = content;
        this.storeName = storeName;
        this.region = region;
    }

    public void setReceiptImage(ReceiptImage ri){
        this.receiptImage = ri;
    }
}
