package com.alltruth.api.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user_image")
@Getter
@Entity
public class UserImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_image_id")
    private Long id;
    private String url;
    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private UserImage(String name, String url, User user){
        this.name = name;
        this.url = url;
        this.user = user;
        user.setImage(this);
    }
}
