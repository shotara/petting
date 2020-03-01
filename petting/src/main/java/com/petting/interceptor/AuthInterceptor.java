package com.petting.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.petting.config.ErrorCode;
import com.petting.config.PettingException;
import com.petting.model.domain.Mmbr;
import com.petting.service.AuthService;
import com.petting.service.MemberService;
import com.petting.util.EncryptUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private MemberService memberService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String encryptedApiAccessToken = request.getHeader("Authorization") != null ? request.getHeader("Authorization").substring(7) : null; //  Bearer {token}을 토큰으로 받아옴
        String encryptedMemberAccessToken = request.getHeader("Pttng-Member-Token") != null ? request.getHeader("Pttng-Member-Token") : null;

        // API 액세스 토큰 체크
        if(this.isApiTokenRequiredMethod(request, handler)) {

            if(StringUtils.isNotEmpty(encryptedApiAccessToken)) {
                if(!authService.checkAPIToken(encryptedApiAccessToken)) {
                    return super.preHandle(request, response, handler);
                }
            }
        }

        // 회원 액세스 토큰 체크
        if(this.isLoginRequired(request, handler)) {
        	Mmbr member = null;
            if(StringUtils.isNotEmpty(encryptedMemberAccessToken)) {
                try {
                    if(!authService.checkAPIToken(encryptedMemberAccessToken)) {
                        return super.preHandle(request, response, handler);
                    }

                    String decryptedMemberAccessToken = EncryptUtil.decodeBase64(encryptedMemberAccessToken);
                    member = memberService.getMemberByMemberAccessToken(decryptedMemberAccessToken);
                  
                } catch(Exception e) {
                	System.out.println(e.toString());
                }
                
                if(member==null)
                	return false;
                
                memberService.setLoginSession(member);
            }

        } 

        return true;
    }


	private boolean isLoginRequired(HttpServletRequest request, Object handler) {
        
		HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PreAuth.class)) {
            PreAuth preAuth = method.getAnnotation(PreAuth.class);

            return ArrayUtils.contains(preAuth.value(), PettingAuthType.LOGIN_REQUIRED);
        }

        return false;
    }


	private boolean isApiTokenRequiredMethod(HttpServletRequest request, Object handler) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(PreAuth.class)) {
            PreAuth preAuth = method.getAnnotation(PreAuth.class);
            return ArrayUtils.contains(preAuth.value(), PettingAuthType.API_KEY);
        }

        return false;
	}
}
