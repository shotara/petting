package com.petting.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 성공인 경우
    E200 (200, "SUCCESS", "SUCCESS"),

    // 인증 관련 에러
    E1000 (1000, "Not Found API Token.", "ERROR"),
    E1100 (1100, "Http Request Not Readable.", "ERROR"),
    E1001 (1101, "Failed to Decrypt API Token ", "ERROR"),
    E1002 (1102, "Expired API Token ", "ERROR"),
    E1200 (1200, "Failed to Pass Values Check.", "ERROR"),

    // 알려지지 않은 에러 관련
    E5000 (5000, "Unknown Error.", "ERROR"),
    E5001 (5001, "DB Error - Insert.", "ERROR"),
    E5002 (5002, "DB Error - Select.", "ERROR"),
    E5003 (5003, "DB Error - Update.", "ERROR"),
    E5004 (5004, "DB Error - Delete.", "ERROR");
  
    private int code;
    private String message;
    private String result;

    private static final Map<Integer, ErrorCode> lookup = new ConcurrentHashMap<>();

    static {
        for (ErrorCode d : ErrorCode.values()) {
            lookup.put(d.getCode(), d);
        }
    }

    ErrorCode(int code, String message, String result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static ErrorCode get(int code) {
        return lookup.get(code);
    }

    public static String getErrorMessage(int code) {
        return get(code).getMessage();
    }
}