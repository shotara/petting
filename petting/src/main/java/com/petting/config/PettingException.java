package com.petting.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class PettingException extends RuntimeException {

    private int code;

    private String message;

    private String result;

    public PettingException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), errorCode.getResult());
    }

    public PettingException(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), message, errorCode.getResult());
    }
}