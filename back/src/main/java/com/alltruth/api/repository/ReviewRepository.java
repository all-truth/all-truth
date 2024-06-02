package com.alltruth.api.repository;

import com.alltruth.api.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r " +
            "from Review r " +
            "left join fetch r.reviewImages a " +
            "left join fetch r.receiptImage " +
            "left join fetch r.user " +
            "where r.title like %:search% " +
            "or r.content like %:search% " +
            "or r.storeName like %:search% " +
            "or r.region like %:search%"
    )
    List<Review> findByTitleOrContent(@Param("search") String search);

    @Query("select r " +
            "from Review r " +
            "left join fetch r.reviewImages a " +
            "left join fetch r.receiptImage " +
            "left join fetch r.user " +
            "where r.id = :reviewId "
    )
    Optional<Review> findByIdFetchJoin(@Param("reviewId") Long reviewId);

    @Query("select r " +
            "from Review r " +
            "left join fetch r.reviewImages a " +
            "left join fetch r.receiptImage " +
            "left join fetch r.user " +
            "where r.title like %:search% " +
            "or r.content like %:search% " +
            "or r.storeName like %:search% " +
            "or r.region like %:search%"
    )
    Page<Review> findBySearchPageable(@Param("search") String search, Pageable pageable);

    @Query("select r " +
            "from Review r " +
            "left join fetch r.reviewImages a " +
            "left join fetch r.receiptImage " +
            "left join fetch r.user ")
    List<Review> findAllFetchJoin();

    @Query("select r " +
            "from Review r " +
            "left join fetch r.reviewImages a " +
            "left join fetch r.receiptImage " +
            "left join fetch r.user ")
    Page<Review> findAllFetchJoinPageable(Pageable pageable);

    @Query("select r " +
            "from Review r " +
            "left join fetch r.reviewImages  " +
            "left join fetch r.receiptImage " +
            "left join fetch r.user u " +
            "where u.id = :userId ")
    Page<Review> findPagingReviewsByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("select r " +
            "from Review r " +
            "left join fetch r.reviewImages  " +
            "left join fetch r.receiptImage " +
            "left join fetch r.user u " +
            "where u.id = :userId ")
    List<Review> findAllReviewsByUserId(@Param("userId") Long userId);


}
