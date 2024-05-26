package com.alltruth.api.config.common.exceptions;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private ErrorCode errorCode;

    public GlobalException(String message){
        super(message);
    }

    public GlobalException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GlobalException(String message, Throwable cause){
        super(message, cause);
    }

    public GlobalException(ErrorCode errorCode, Throwable cause){
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }




}
