package com.alltruth.api.config.security;

import com.alltruth.api.config.common.exceptions.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 인증에러가 발생했을 때 response를 처리해주는 클래스
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception) throws IOException {
        log.info("CustomAuthenticationEntryPoint ::: 인증에러 발생!!!");
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", ErrorCode.UNAUTHENTICATION.getStatus().value());
        responseBody.put("message", ErrorCode.UNAUTHENTICATION.getMessage());
        res.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }
}
