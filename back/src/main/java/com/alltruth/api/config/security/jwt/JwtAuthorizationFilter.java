package com.alltruth.api.config.security.jwt;

import com.alltruth.api.config.common.exceptions.ErrorCode;
import com.alltruth.api.config.common.exceptions.GlobalException;
import com.alltruth.api.config.security.CustomDeniedHandler;
import com.alltruth.api.config.security.auth.PrincipalDetails;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 권한이나 인증이 필요한 특정 주소를 요청했을 때 타는 필터
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private CustomDeniedHandler customDeniedHandler;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider, CustomDeniedHandler customDeniedHandler) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customDeniedHandler = customDeniedHandler;
    }

    // 인증이나 권한이 필요한 주소에 요청이 왔을 때 해당 필터를 타게됨
    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException, AccessDeniedException {
        String jwtHeader = request.getHeader("Authorization");
        // 인증 헤더가 없거나 Bearer이 아니면 그냥 진행
        // 만약 회원가입에 Authorization 헤더가 있으면 검증을 시도함 개선이 필요
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer") || request.getRequestURL().indexOf("join") != -1){
            filterChain.doFilter(request, response);
            return;
        }

        // JWT 토큰을 검증하여 사용자 확인
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        // 유효한 토큰인지 확인
        try{
            if(!jwtTokenProvider.validToken(jwtToken)) throw new Exception("토큰이 유효하지 않음");
        }catch(Exception e){
            customDeniedHandler.handle(request, response, new GlobalException(ErrorCode.ACCESS_TOKEN_NOT_VALIDATE));
            return;
        }

        Long userId = jwtTokenProvider.getUserId(jwtToken);

        if(userId != null){
            User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            // Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어줌
            // 직접 만들어주는 authentication 객체 이때 로그인이 아니고 확인만 하는 것이라 password는 필요 없음.

            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 시큐리티 세션공간에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }
        filterChain.doFilter(request, response);
    }

}
