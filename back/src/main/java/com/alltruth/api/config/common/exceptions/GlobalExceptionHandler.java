package com.alltruth.api.config.common.exceptions;

import com.alltruth.api.config.common.exceptions.file.StorageException;
import com.alltruth.api.config.common.exceptions.file.StorageFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.SocketException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException e){
        log.error("handleGlobalException::: {}", e.getErrorCode());

        return ResponseEntity
                .status(e.getErrorCode().getStatus().value())
                .body(new ErrorResponse(e.getErrorCode()));
    }

    // 파일 에러
    @ExceptionHandler({StorageException.class, StorageFileNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleFileException(RuntimeException e){
        log.error("handleGlobalException::: {}", e.getMessage());

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    // 로그인 관련 에러
    @ExceptionHandler({InternalAuthenticationServiceException.class, BadCredentialsException.class, AuthenticationException.class})
    protected ResponseEntity<ErrorResponse> handleInternalAuthenticationServiceException(){

        return ResponseEntity
                .status(ErrorCode.USER_NOT_MATCH.getStatus().value())
                .body(new ErrorResponse(ErrorCode.USER_NOT_MATCH));
    }

    // 500 에러
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e){
        log.error("handleException::: {}", e.getMessage());

        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
