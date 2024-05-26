package com.alltruth.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;
    @Column(unique = true)
    private String loginId;
    private String password;
    private String nickname;
    private String roles; // User, Admin

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> commentList = new ArrayList<>();



    public List<String> getRoleList(){
        if(roles.length() > 0){
            return Arrays.asList(roles.split(","));
        }
        return new ArrayList<>();
    }

    @Builder
    public User(String loginId, String password, String roles, String nickname){
        this.loginId = loginId;
        this.password = password;
        this.roles = roles;
        this.nickname = nickname;
    }


}


//@Getter
//@Entity
//class Team{
//    @Id
//    private Long id;
//
//    private String name;
//
//    @OneToMany
//    @JoinColumn(name ="test_id")
//    private List<Member> members = new ArrayList<>();
//}
//
//@Entity
//class Member {
//    @Id
//    private Long id;
//
//    private String username;
//}
//
//
