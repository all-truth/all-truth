package com.alltruth.api.repository;

import com.alltruth.api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserId(Long id);
    List<Comment> findAllByReviewId(Long id);
}
