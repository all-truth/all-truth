package com.alltruth.api.controller;

import com.alltruth.api.dto.ReviewDTO;
import com.alltruth.api.service.FileUploadService;
import com.alltruth.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final FileUploadService fileUploadService;
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity writeReview(@RequestPart("reviewReq") ReviewDTO.ReviewReq reviewReq,
                            @RequestPart(value = "images", required = false) MultipartFile[] images,
                            @RequestPart(value = "receiptImage", required = false) MultipartFile receiptImage) {
        System.out.println("controller::: ");
        System.out.println(images);
        System.out.println(receiptImage);
        ReviewDTO.ReviewRes res = reviewService.writeReview(reviewReq, images, receiptImage);
        // 리뷰 작성하면 작성된 리뷰

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @PutMapping("/review/{id}")
    public ResponseEntity<ReviewDTO.ReviewRes> updateReview(@PathVariable("id") Long id,
                             @RequestPart("reviewReq") ReviewDTO.ReviewReq reviewReq,
                             @RequestPart(value = "images", required = false) MultipartFile[] images,
                             @RequestPart(value = "receiptImage", required = false) MultipartFile receiptImage){

        ReviewDTO.ReviewRes updatedReview = reviewService.updateReview(id, reviewReq, images, receiptImage);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity deleteReview(@PathVariable("id") Long id){
        reviewService.deleteReview(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDTO.ReviewRes>> getReviews(){
        List<ReviewDTO.ReviewRes> res = reviewService.getReviewList();
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/review/img/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String fileName) {
        Resource res = reviewService.getImage(fileName);


        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(res);
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewDTO.ReviewRes>> searchReview(@RequestParam(name="search", required = false, defaultValue = "") String search){
        List<ReviewDTO.ReviewRes> res = reviewService.searchReviewByKeyword(search);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/page-review")
    public ResponseEntity<ReviewDTO.PageReviewRes> searchPagingReview(@RequestParam(name="search", defaultValue = "") String search,
                                             @RequestParam(name="page", required = false, defaultValue = "1") Integer page,
                                             @RequestParam(name="size", required = false, defaultValue = "40") Integer size){
        ReviewDTO.PageReviewRes res = reviewService.searchPagenationReviewByKeyword(search, page, size);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<ReviewDTO.ReviewRes> getReviewByReviewId(@PathVariable("id") Long id){
        ReviewDTO.ReviewRes review = reviewService.getReviewByReviewId(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/page-reviews")
    public ResponseEntity<ReviewDTO.PageReviewRes> getPagingReviews(@RequestParam(name="page", required = false, defaultValue = "1") Integer page,
                                           @RequestParam(name="size", required = false, defaultValue = "40") Integer size){
        ReviewDTO.PageReviewRes res = reviewService.pagenationReview(page, size);
        return ResponseEntity.ok(res);
    }

}
