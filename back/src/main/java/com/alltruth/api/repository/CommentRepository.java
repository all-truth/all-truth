package com.alltruth.api.repository;

import com.alltruth.api.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserId(Long id);
    List<Comment> findAllByReviewId(Long id);

    @Query("select c " +
            "from Comment c " +
            "join fetch c.user " +
            "where c.review.id = :reviewId")
    List<Comment> findAllByReviewIdFetchJoinUser(@Param("reviewId") Long reviewId);

    @Query("select c " +
            "from Comment c " +
            "join fetch c.user u " +
            "where u.id = :userId")
    List<Comment> findAllByUserIdFetchJoin(@Param("userId") Long userId);

    @Query("select c " +
            "from Comment c " +
            "join fetch c.user u " +
            "where u.id = :userId")
    Page<Comment> findPagingByUserId(@Param("userId") Long userId, Pageable pageable);
}
