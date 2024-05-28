package com.alltruth.api.repository;

import com.alltruth.api.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r " +
            "from Review r " +
            "where r.title like %:search% " +
            "or r.content like %:search% " +
            "or r.storeName like %:search% " +
            "or r.region like %:search% "
    )
    List<Review> findByTitleOrContent(@Param("search") String search);

//    @Query("select r " +
//            "from Review r " +
//            "where r.title like %:title% ")
//    List<Review> findByTitleOrContent(String title);
//
//    @Query("select r " +
//            "from Review r " +
//            "where r.title like %:title% ")
//    List<Review> findByContent(String title);


}
