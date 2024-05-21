package com.alltruth.api.config.security.jwt;

import com.alltruth.api.config.security.auth.PrincipalDetails;
import com.alltruth.api.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

// 시큐리티에서 username, password를 받아주는 필터
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // 로그인 요청을 하면 실행되는 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{

        try{
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println("로그인 요청:: " + user);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getLoginId(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            return authentication;
        } catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 아래 메서드가 실행됨
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response를 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)throws IOException, ServletException{
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("승윤")
                .setExpiration(new Date(new Date().getTime() + Duration.ofMinutes(1).toMillis()))
                .setSubject(principalDetails.getUser().getId().toString())
                .claim("id", principalDetails.getUser().getId())
                .claim("username", principalDetails.getUser().getLoginId())
                .signWith(SignatureAlgorithm.HS256, "secretkey")
                .compact();

        System.out.println("successfulAuthentication 토큰 생성   " + jwtToken);
        // 사용자 헤더에 응답시킴
        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
