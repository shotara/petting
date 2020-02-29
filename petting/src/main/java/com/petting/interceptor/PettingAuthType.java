package com.petting.interceptor;

public enum PettingAuthType {

    // API KEY 가 있는지 정상인지 검증한다.
	API_KEY,
    // 사용자가 반드시 로그인되어 있어야 한다.
    LOGIN_REQUIRED,

}
