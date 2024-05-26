package com.alltruth.api.service;

import com.alltruth.api.config.security.SecurityConfig;
import com.alltruth.api.dto.CommentDTO;
import com.alltruth.api.entity.Comment;
import com.alltruth.api.entity.Review;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.CommentRepository;
import com.alltruth.api.repository.ReviewRepository;
import com.alltruth.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<CommentDTO.CommentRes> getCommentsById(Long id){
        List<Comment> comments = commentRepository.findAllByReviewId(id);
        List<CommentDTO.CommentRes> res = comments.stream().map((item) ->{
            User user = item.getUser();
            CommentDTO.CommentRes comment = CommentDTO.CommentRes.builder()
                    .id(item.getId())
                    .content(item.getContent())
                    .nickname(user.getNickname())
                    .userId(user.getId())
                    .build();
            return comment;
        }).toList();

        return res;
    }

    @Transactional
    public CommentDTO.CommentRes writeComment(Long reviewId, CommentDTO.CommentReq req){
        Long userId = SecurityConfig.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다!"));

        System.out.println(user);
        System.out.println(user.getId());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new IllegalArgumentException("해당 리뷰가 존재하지 않습니다!!"));

        Comment comment = Comment.builder()
                .content(req.getContent())
                .review(review)
                .user(user)
                .build();

        Comment commentEntity = commentRepository.save(comment);

        return CommentDTO.CommentRes.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .build();
    }

    @Transactional
    public CommentDTO.CommentRes updateComment(Long reviewId, CommentDTO.CommentReq req){
        Long userId = SecurityConfig.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다!"));

        Comment comment = commentRepository.findById(reviewId).orElseThrow(()-> new IllegalArgumentException("해당 댓글 정보가 존재하지 않습니다!"));

        if(comment.getUser().getId() != user.getId()) throw new IllegalArgumentException("해당 댓글 작성자가 일치하지 않습니다!");

        comment.updateComment(req.getContent());
        commentRepository.save(comment);

        return CommentDTO.CommentRes.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .id(comment.getId())
                .content(comment.getContent())
                .build();
    }

    public void deleteComment(Long reviewId){
        Long userId = SecurityConfig.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다!"));

        Comment comment = commentRepository.findById(reviewId).orElseThrow(()-> new IllegalArgumentException("해당 댓글 정보가 존재하지 않습니다!"));

        if(comment.getUser().getId() != user.getId()) throw new IllegalArgumentException("해당 댓글 작성자가 일치하지 않습니다!");

        commentRepository.delete(comment);
    }
}
