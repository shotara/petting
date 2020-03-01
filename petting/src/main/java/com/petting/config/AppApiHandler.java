package com.petting.config;

import org.json.simple.JSONObject;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * App Exception 관련 Handler
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class AppApiHandler {

    public static final String API_RESPONSE = "response";
    public static final String API_MESSAGE = "message";

    @ModelAttribute
    public ModelMap resultMap() {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("code", ErrorCode.E200.getCode());
        resultMap.addAttribute("result", ErrorCode.E200.getResult());
        return resultMap;
    }

    @ExceptionHandler(PettingException.class)
    public ModelMap resultCode(PettingException PettingException) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("code", PettingException.getCode());
        modelMap.addAttribute("result", PettingException.getResult());
        modelMap.addAttribute("message", PettingException.getMessage());
        modelMap.addAttribute("response", new JSONObject());
        return modelMap;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ModelMap badRequest() {
        log.warn(ErrorCode.E1000.getMessage());
        return errorModel(ErrorCode.E1000);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelMap typeMismatch() {
        log.warn(ErrorCode.E1100.getMessage());
        return errorModel(ErrorCode.E1100);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelMap missingParameters() {
        log.warn(ErrorCode.E1200.getMessage());
        return errorModel(ErrorCode.E1200);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ModelMap dataIntegrity() {
//        log.warn("DataIntegrityViolationException");
//        return errorModel(ErrorCode.E5005);
//    }

    private ModelMap errorModel(ErrorCode errorCode) {
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("code", errorCode.getCode());
        modelMap.addAttribute("result", errorCode.getResult());
        modelMap.addAttribute("message", errorCode.getMessage());
        modelMap.addAttribute("response", new JSONObject());

        return modelMap;
    }
}