package com.alltruth.api.config.security;

import com.alltruth.api.config.common.exceptions.ErrorCode;
import com.alltruth.api.config.common.exceptions.GlobalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CustomDeniedHandler implements AccessDeniedHandler {
    // 403, 또는 인가 에러 응답을 해주는 클래스
    @Override
    public void handle(HttpServletRequest req,
                       HttpServletResponse res,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.info("CustomDeniedHandler ::: 인가에러 발생!!!");
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", ErrorCode.UNAUTHORIZATION.getStatus().value());
        responseBody.put("message", ErrorCode.UNAUTHORIZATION.getMessage());
        res.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }

    public void handle(HttpServletRequest req,
                       HttpServletResponse res,
                       GlobalException exception) throws IOException {
        log.info("CustomDeniedHandler ::: 인가에러 발생!!!");
        log.info("CustomDeniedHandler ::: {}", exception.getMessage());
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", exception.getErrorCode().getStatus().value());
        responseBody.put("message", exception.getErrorCode().getMessage());
        res.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }


}
