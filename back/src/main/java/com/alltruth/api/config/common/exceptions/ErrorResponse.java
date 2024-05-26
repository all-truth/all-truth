package com.alltruth.api.config.common.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timeStamp = LocalDateTime.now();
    private final int status;
    private final String message;

    public ErrorResponse(ErrorCode errorCode){
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
    }
}
