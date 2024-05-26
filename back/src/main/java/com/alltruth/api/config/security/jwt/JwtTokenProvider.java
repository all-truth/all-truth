package com.alltruth.api.config.security.jwt;

import com.alltruth.api.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
//
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getId().toString())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretkey())
                .compact();
    }

    public boolean validToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretkey())
                    .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            System.out.println("토큰파싱 실패");
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get("auth").toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(
                claims.getSubject(), "", authorities), token, authorities);
    }

    // 4 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretkey())
                .parseClaimsJws(token)
                .getBody();
    }

}
