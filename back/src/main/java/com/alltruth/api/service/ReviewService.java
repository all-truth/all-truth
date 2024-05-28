package com.alltruth.api.service;

import com.alltruth.api.config.common.exceptions.ErrorCode;
import com.alltruth.api.config.common.exceptions.GlobalException;
import com.alltruth.api.config.file.FileUploadUtil;
import com.alltruth.api.config.security.SecurityConfig;
import com.alltruth.api.dto.ReviewDTO;
import com.alltruth.api.entity.ReceiptImage;
import com.alltruth.api.entity.Review;
import com.alltruth.api.entity.ReviewImage;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.ReceiptImageRepository;
import com.alltruth.api.repository.ReviewImageRepository;
import com.alltruth.api.repository.ReviewRepository;
import com.alltruth.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final FileUploadUtil fileUploadUtil;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final ReceiptImageRepository receiptImageRepository;


    @Transactional
    public ReviewDTO.ReviewRes writeReview(ReviewDTO.ReviewReq reviewReq,
                            MultipartFile[] images,
                            MultipartFile receiptImage){

        User user = userRepository.findById(SecurityConfig.getUserId())
                .orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));


        Review review = Review.builder()
                .title(reviewReq.getTitle())
                .content(reviewReq.getContent())
                .region(reviewReq.getRegion())
                .storeName(reviewReq.getStoreName())
                .user(user)
                .build();

        System.out.println(images);
        // 리뷰 이미지들 파일 생성
        if(images != null){

            Arrays.stream(images).forEach((file) ->{
                if(!file.isEmpty()){
                    Path filePath = fileUploadUtil.store(file);
                    ReviewImage reviewImage = ReviewImage.builder()
                            .name(filePath.getFileName().toString())
                            .review(review)
                            .url("http://localhost:8080/review/img/" +filePath.getFileName().toString() )
                            .build();

                    // 근데 이렇게 하나하나 save를 해줘야하나? review를 기준으로 save를 못 하나? > CascadtType.PERSIST로 설정해서 해결
                    //reviewImageRepository.save(reviewImage);

                }
            });
        }

        if(receiptImage != null && !receiptImage.isEmpty()){
            Path filePath = fileUploadUtil.store(receiptImage);
            ReceiptImage receiptImageEntity = ReceiptImage.builder()
                    .name(filePath.getFileName().toString())
                    .url("http://localhost:8080/review/img/" +filePath.getFileName().toString())
                    .review(review)
                    .build();
            //receiptImageRepository.save(receiptImageEntity);

        }

        reviewRepository.save(review);
        ReviewDTO.ReviewRes res = new ReviewDTO.ReviewRes().toReviewResByReview(review);

        return res;
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO.ReviewRes> getReviewList(){
        List<Review> reviews = reviewRepository.findAll();

        List<ReviewDTO.ReviewRes> reviewRes = reviews.stream().map((item)->{
            ReviewDTO.ReviewRes res = new ReviewDTO.ReviewRes().toReviewResByReview(item);
            return res;
        }).toList();

        return reviewRes;
    }

    @Transactional
    public void deleteReview(Long reviewId){

        User user = userRepository.findById(SecurityConfig.getUserId())
                .orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new GlobalException(ErrorCode.REVIEW_NOT_FOUND));

        if(user.getId() != review.getUser().getId()) throw new GlobalException(ErrorCode.REVIEW_NOT_AUTHOR);

        deleteImageFiles(review);

        reviewRepository.deleteById(reviewId);
    }

    @Transactional
    public ReviewDTO.ReviewRes updateReview(Long reviewId,
                                            ReviewDTO.ReviewReq reviewReq,
                                            MultipartFile[] images,
                                            MultipartFile receiptImage){
        User user = userRepository.findById(SecurityConfig.getUserId())
                .orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));



        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new GlobalException(ErrorCode.REVIEW_NOT_FOUND));

        if(user.getId() != review.getUser().getId()) throw new GlobalException(ErrorCode.REVIEW_NOT_AUTHOR);

        // 기존에 있던 이미지 파일 삭제
        deleteImageFiles(review);

        // 리뷰 엔티티 업데이트
        review.update(reviewReq.getTitle(),
                reviewReq.getContent(),
                reviewReq.getStoreName(),
                reviewReq.getRegion());

        // 리뷰 이미지들 파일 생성
        if(images != null){
            Arrays.stream(images).forEach((file) ->{
                if(!file.isEmpty()){
                    Path filePath = fileUploadUtil.store(file);
                    ReviewImage reviewImage = ReviewImage.builder()
                            .name(filePath.getFileName().toString())
                            .review(review)
                            .url("http://localhost:8080/review/img/" +filePath.getFileName().toString() )
                            .build();

                    // 근데 이렇게 하나하나 save를 해줘야하나? review를 기준으로 save를 못 하나? > CascadtType.PERSIST로 설정해서 해결
                    //reviewImageRepository.save(reviewImage);

                }
            });
        }

        if(receiptImage != null && !receiptImage.isEmpty()){
            Path filePath = fileUploadUtil.store(receiptImage);
            ReceiptImage receiptImageEntity = ReceiptImage.builder()
                    .name(filePath.getFileName().toString())
                    .url("http://localhost:8080/review/img/" +filePath.getFileName().toString())
                    .review(review)
                    .build();
            //receiptImageRepository.save(receiptImageEntity);

        }

        Review reviewRes = reviewRepository.save(review);

        ReviewDTO.ReviewRes res = new ReviewDTO.ReviewRes().toReviewResByReview(reviewRes);

        return res;
    }

    @Transactional(readOnly = true)
    public ReviewDTO.ReviewRes getReviewByReviewId(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(()->new GlobalException(ErrorCode.REVIEW_NOT_FOUND));
        return new ReviewDTO.ReviewRes().toReviewResByReview(review);
    }

    public void searchReviewByKeyword(String keyword){
        List<Review> reviewList = reviewRepository.findByTitleOrContent(keyword);

        System.out.println(reviewList.size());
    }

    public Resource getImage(String fileName){
        Resource res = fileUploadUtil.loadAsResource(fileName);
        return res;
    }

    public void deleteImageFiles(Review review){
        review.getReviewImages().forEach((reviewImage)->{
            fileUploadUtil.delete(reviewImage.getName());
        });
        if(review.getReceiptImage() != null)  fileUploadUtil.delete(review.getReceiptImage().getName());
    }


}
