package com.alltruth.api.service;

import com.alltruth.api.config.common.exceptions.ErrorCode;
import com.alltruth.api.config.common.exceptions.GlobalException;
import com.alltruth.api.config.security.SecurityConfig;
import com.alltruth.api.dto.CommentDTO;
import com.alltruth.api.entity.Comment;
import com.alltruth.api.entity.Review;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.CommentRepository;
import com.alltruth.api.repository.ReviewRepository;
import com.alltruth.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        // 일반 join
        //List<Comment> comments = commentRepository.findAllByReviewId(id);
        // fetch join 쿼리
        List<Comment> comments = commentRepository.findAllByReviewIdFetchJoinUser(id);
        List<CommentDTO.CommentRes> res = comments.stream().map((item) ->{
            User user = item.getUser();
            CommentDTO.CommentRes comment = CommentDTO.CommentRes.builder()
                    .id(item.getId())
                    .content(item.getContent())
                    .nickname(user.getNickname())
                    .userId(user.getId())
                    .image(user.getImage() != null ? user.getImage().getUrl() : "")
                    .build();
            return comment;
        }).toList();

        return res;
    }

    @Transactional
    public CommentDTO.CommentRes writeComment(Long reviewId, CommentDTO.CommentReq req){

        System.out.println("시큐리티 config 에서 가져온 user :::");
        Long userId = SecurityConfig.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));

        System.out.println(user);
        System.out.println(user.getId());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new GlobalException(ErrorCode.REVIEW_NOT_FOUND));

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
                .image(user.getImage() != null ? user.getImage().getUrl() : "")
                .build();
    }

    @Transactional
    public CommentDTO.CommentRes updateComment(Long commentId, CommentDTO.CommentReq req){
        Long userId = SecurityConfig.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        if(comment.getUser().getId() != user.getId()) throw new GlobalException(ErrorCode.COMMENT_NOT_AUTHOR);

        comment.updateComment(req.getContent());
        commentRepository.save(comment);

        return CommentDTO.CommentRes.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .id(comment.getId())
                .content(comment.getContent())
                .image(user.getImage() != null ? user.getImage().getUrl() : "")
                .build();
    }

    @Transactional
    public void deleteComment(Long reviewId){
        Long userId = SecurityConfig.getUserId();
        User user = userRepository.findById(userId).orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));

        Comment comment = commentRepository.findById(reviewId).orElseThrow(()-> new GlobalException(ErrorCode.COMMENT_NOT_FOUND));

        if(comment.getUser().getId() != user.getId()) throw new GlobalException(ErrorCode.COMMENT_NOT_AUTHOR);

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentDTO.CommentRes> getMyComments(){
        Long userId = SecurityConfig.getUserId();

        List<Comment> comments = commentRepository.findAllByUserIdFetchJoin(userId);
        List<CommentDTO.CommentRes> res = comments.stream().map((item) ->{
            User user = item.getUser();
            CommentDTO.CommentRes comment = CommentDTO.CommentRes.builder()
                    .id(item.getId())
                    .content(item.getContent())
                    .nickname(user.getNickname())
                    .userId(user.getId())
                    .image(user.getImage() != null ? user.getImage().getUrl() : "")
                    .build();
            return comment;
        }).toList();

        return res;
    }

    @Transactional(readOnly = true)
    public CommentDTO.PageCommentRes getMyPagingComments(Integer page, Integer size){
        Long userId = SecurityConfig.getUserId();
        Pageable pageRes = PageRequest.of(page - 1,size);
        Page<Comment> res = commentRepository.findPagingByUserId(userId, pageRes);

        return new CommentDTO.PageCommentRes().toCommentResByPage(res);
    }
}
