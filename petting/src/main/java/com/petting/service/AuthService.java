package com.petting.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petting.config.ErrorCode;
import com.petting.config.GlobalConfig;
import com.petting.config.PettingException;
import com.petting.model.AuthMapper;
import com.petting.model.domain.ApiTokn;
import com.petting.util.CommonUtil;
import com.petting.util.EncryptUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class AuthService {
	
	@Autowired
	private AuthMapper authMapper;
	
    public boolean checkAPIToken(String encryptedApiAccessToken) {

        // API 토큰 여부 확인
        if(encryptedApiAccessToken == null) {
            log.error(ErrorCode.E1000.getMessage() + " (API TOKEN = {})", encryptedApiAccessToken);
            throw new PettingException(ErrorCode.E1000);
        }
        
        // API 액세스 토큰 복호화
        String decryptedApiAccessToken = EncryptUtil.decodeBase64(encryptedApiAccessToken);
        if(decryptedApiAccessToken == null) {
            log.error(ErrorCode.E1001.getMessage() + " (API TOKEN = {})", encryptedApiAccessToken);
            throw new PettingException(ErrorCode.E1001);
        }

        // API 액세스 토큰 패턴 체크
        // todo:

        
        // API 액세스 토큰 가져오기
        ApiTokn apiToken = authMapper.getApiToken(decryptedApiAccessToken);
        if(apiToken == null) {
            log.error(ErrorCode.E1000.getMessage() + " (API TOKEN = {})", decryptedApiAccessToken);
            throw new PettingException(ErrorCode.E1000);
        }

        // API 액세스 토큰 유효기간 체크
        if(apiToken.getApiToknExprDtm().getTime() < System.currentTimeMillis()) {
            log.error(ErrorCode.E1002.getMessage() + " (API TOKEN = {})", decryptedApiAccessToken);
            throw new PettingException(ErrorCode.E1002);
        }

        // 연장 시각 계산
        Date expiredDate = CommonUtil.generateExpiredDate(GlobalConfig.GLOBAL_API_TOKEN_EXPIRE_IN);

        // 해당 토큰 연장
        if(authMapper.updateApiToken(decryptedApiAccessToken, expiredDate) != 1) {
            log.error(ErrorCode.E5003.getMessage() + " (API TOKEN = {})", decryptedApiAccessToken);
            throw new PettingException(ErrorCode.E5003);
        }

        return true;
    }
}
