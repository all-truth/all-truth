package com.alltruth.api.config.security.jwt;

import com.alltruth.api.config.security.auth.PrincipalDetails;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 권한이나 인증이 필요한 특정 주소를 요청했을 때 타는 필터
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // 인증이나 권한이 필요한 주소에 요청이 왔을 때 해당 필터를 타게됨
    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws IOException, ServletException {
        String jwtHeader = request.getHeader("Authorization");

        // 인증 헤더가 없거나 Bearer이 아니면 그냥 진행
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }

        // JWT 토큰을 검증하여 사용자 확인
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");

        if(!jwtTokenProvider.validToken(jwtToken)) new UsernameNotFoundException("사용자를 찾을 수 없습니다.");

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
