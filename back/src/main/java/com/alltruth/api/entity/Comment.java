package com.alltruth.api.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="review_id")
    private Review review;

    @Builder
    private Comment(String content, User user, Review review){
        this.content = content;
        this.user = user;
        this.review = review;

        user.getCommentList().add(this);
        review.getComments().add(this);
    }

    public void updateComment(String content){
        this.content = content;
    }
}
