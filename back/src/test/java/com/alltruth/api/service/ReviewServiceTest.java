package com.alltruth.api.service;

import com.alltruth.api.config.security.auth.PrincipalDetails;
import com.alltruth.api.dto.ReviewDTO;
import com.alltruth.api.entity.Review;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.ReviewRepository;
import com.alltruth.api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest // 테스트용 어플리케이션 컨텍스트
@AutoConfigureMockMvc
public class ReviewServiceTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    User userEntity;

    @BeforeEach
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();

        // 사용자 정보 설정
        userEntity = User.builder()
                .loginId("test")
                .password(bCryptPasswordEncoder.encode("test"))
                .roles("ROLE_USER")
                .nickname("test")
                .build();

        userRepository.save(userEntity);

        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);



        // SecurityContext에 인증 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Review review1 = Review.builder()
                .title("제목1")
                .content("내용1")
                .user(userEntity)
                .build();

        Review review2 = Review.builder()
                .title("제목1")
                .content("내용1")
                .user(userEntity)
                .build();

        Review review3 = Review.builder()
                .title("제목1")
                .content("내용1")
                .user(userEntity)
                .build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);
    }

    @AfterEach
    public void clearUp(){
        reviewRepository.deleteAll();
        userRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @DisplayName("4개의 리뷰 리스트 반환")
    @Test
    public void getReviewList() throws Exception{
        // Given
        Review review = Review.builder()
                .title("제목1")
                .content("내용1")
                .user(userEntity)
                .build();

        reviewRepository.save(review);

        // when
        List<ReviewDTO.ReviewRes> res = reviewService.getReviewList();

        // then
        assertThat(res.size()).isEqualTo(4);
    }

    @DisplayName("3개의 리뷰 리스트 반환")
    @Test
    public void getReviewList2() throws Exception{

        // when
        List<ReviewDTO.ReviewRes> res = reviewService.getReviewList();

        // then
        assertThat(res.size()).isEqualTo(3);
    }

    @DisplayName("이미지 포함 리뷰 작성하기")
    @Test
    public void writeReviewWithImages() throws Exception{
        MultipartFile file1 = new MockMultipartFile(
                "file",                        // 필드 이름
                "testfile.txt",                // 파일 이름
                "text/plain",                  // 파일 유형
                "This is the file content.".getBytes(StandardCharsets.UTF_8) // 파일 내용
        );

        MultipartFile file2 = new MockMultipartFile(
                "file",                        // 필드 이름
                "testfile.txt",                // 파일 이름
                "text/plain",                  // 파일 유형
                "This is the file content.".getBytes(StandardCharsets.UTF_8) // 파일 내용
        );

        MultipartFile[] files = {file1, file2};

        ReviewDTO.ReviewReq req = new ReviewDTO.ReviewReq("테스트제목1", "테스트내용1", "테스트가게이름1", "테스트지역1");


        // when
        ReviewDTO.ReviewRes res = reviewService.writeReview(req,files, file1);

        // then
        assertThat(res.getContent()).isEqualTo(req.getContent());
        assertThat(res.getStoreName()).isEqualTo(req.getStoreName());
        assertThat(res.getTitle()).isEqualTo(req.getTitle());
        assertThat(res.getRegion()).isEqualTo(req.getRegion());
        assertThat(res.getImages().size()).isEqualTo(2);
        assertThat(res.getReceiptImage()).isNotEmpty();
    }

    @DisplayName("이미지를 포함하지 않는 리뷰 작성하기")
    @Test
    public void writeReview() throws Exception{
        MultipartFile file1 = null;
        MultipartFile[] files = null;

        ReviewDTO.ReviewReq req = new ReviewDTO.ReviewReq("테스트제목1", "테스트내용1", "테스트가게이름1", "테스트지역1");


        // when
        ReviewDTO.ReviewRes res = reviewService.writeReview(req,files, file1);

        // then
        assertThat(res.getContent()).isEqualTo(req.getContent());
        assertThat(res.getStoreName()).isEqualTo(req.getStoreName());
        assertThat(res.getTitle()).isEqualTo(req.getTitle());
        assertThat(res.getRegion()).isEqualTo(req.getRegion());
        assertThat(res.getImages().size()).isEqualTo(0);
        assertThat(res.getReceiptImage()).isEqualTo("");
    }

    @DisplayName("리뷰 업데이트 하기")
    @Test
    public void updateReview() throws Exception{
        MultipartFile file1 = null;
        MultipartFile[] files = null;

        ReviewDTO.ReviewReq req = new ReviewDTO.ReviewReq("바뀐제목1", "바뀐내용1", "바뀐가게이름1", "바뀐지역1");


        // when
        ReviewDTO.ReviewRes res = reviewService.updateReview(1l,req,files, file1);

        // then
        assertThat(res.getContent()).isEqualTo(req.getContent());
        assertThat(res.getStoreName()).isEqualTo(req.getStoreName());
        assertThat(res.getTitle()).isEqualTo(req.getTitle());
        assertThat(res.getRegion()).isEqualTo(req.getRegion());
        assertThat(res.getImages().size()).isEqualTo(0);
        assertThat(res.getReceiptImage()).isEqualTo("");
    }

    @DisplayName("리뷰 삭제 하기")
    @Test
    public void deleteReview() throws Exception{

        // when
        List<ReviewDTO.ReviewRes> res = reviewService.getReviewList();
        reviewService.deleteReview(res.get(0).getId());
        List<ReviewDTO.ReviewRes> res2 = reviewService.getReviewList();

        // then
        assertThat(res2.size()).isEqualTo(2);
    }
}
