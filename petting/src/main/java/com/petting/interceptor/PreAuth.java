package com.petting.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * controller 에서 자동으로 auth 검증을 위해 정의한 annotation
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PreAuth {

    PettingAuthType[] value();
}