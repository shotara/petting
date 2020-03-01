package com.petting.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import com.petting.config.GlobalConfig;
import com.petting.config.PettingThread;
import com.petting.controller.dto.MemberDto;
import com.petting.controller.dto.TokenDto;
import com.petting.model.MemberMapper;
import com.petting.model.domain.Mmbr;
import com.petting.util.CommonUtil;
import com.petting.util.EncryptUtil;

import ch.qos.logback.core.subst.Token;

@Service
public class MemberService {

	@Autowired
	private AuthService AuthService;

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private GlobalConfig globalConfig;
	
	
	public Mmbr getMemberByMemberAccessToken(String decryptedMemberAccessToken) {
		return memberMapper.getMemberByMemberAccessToken(decryptedMemberAccessToken);
	}

	public TokenDto setLoginSession(Mmbr member) {
		
		String encryptedApiAccessToken = PettingThread.get().getApiToken() != null ? PettingThread.get().getApiToken() : "";
		
		
        // 필수 변수 체크
        if(StringUtils.isEmpty(encryptedApiAccessToken) || encryptedApiAccessToken.equals("null")) {
            return null;
        }
		
        // 유효기간 설정
        Date expiredAt = CommonUtil.generateExpiredDate(GlobalConfig.GLOBAL_MEMBER_TOKEN_EXPIRE_IN);

        // API 토큰 복호화
        String decryptedApiAccessToken = EncryptUtil.decodeBase64(encryptedApiAccessToken);

//        // 회원 액세스 토큰 생성
//        MemberTokenStore memberTokenStore = authService.generateMemberTokenStore(deviceId, deviceType, member.getMemberSeqNo());
//
//        // 회원 로그인 히스토리 저장
//        if(memberMapper.addMemberSigninHistory(new MemberSigninHistory(member.getMemberSeqNo(), deviceId, deviceType, decryptedApiAccessToken, memberTokenStore.getToken(), method, userAgent, proxyIp, null, null)) != 1) {
//            throw new Exception();
//        }
//        
        
        
		TokenDto tokenDto = new TokenDto(); 
		tokenDto.setMemberId(member.getMmbrId());
		tokenDto.setMemberToken("");
		
		try {
			//멤버 토큰
			tokenDto.setMemberId(EncryptUtil.decodeAES(member.getMmbrId(), globalConfig.GLOBAL_SECURITY_AESKEYS_MEMBER_ID));
			
		} catch (Exception e) {
			
		}
		
		
		
		
		
		return tokenDto;
	}

	public MemberDto joinMember(Map<String, String> parameters) {

        // 필수 변수
        String memberId = parameters.get("member_id") != null ? parameters.get("member_id").toString().trim() : "";
        String password = parameters.get("password") != null ? parameters.get("password").toString().trim() : "";

        // 비필수 변수 
        String phoneNo = parameters.get("phone_no") != null ? parameters.get("phone_no").toString().trim() : "";

        // 파라미터 체크
        ArrayList<Object> unvalidatedValues = new ArrayList<>();
        unvalidatedValues.add(memberId);
        unvalidatedValues.add(password);
        if(!CommonUtil.checkUnvalidatedValues(unvalidatedValues)) {
        	System.out.println("Paramater Error!");
        }
        
        // member_id 중복 체크
        if(!checkMemberParam(1, memberId)) {
        	System.out.println("Already Exist Id");
        }

        // phone_no가 있을 경우
        if(!phoneNo.equals("")) {
        	
        	// phone_id 중복 체크
        	if(!checkMemberParam(1, phoneNo)) {
        		System.out.println("Already Exist Phone No");
        	}
        	
        }
        
		// member 추가
        if(memberMapper.joinMember(memberId, password, phoneNo) != 1) {
        	System.out.println("Join Member DB Error!");
        
        }
        
		return null;
	}
	
	public boolean checkMemberParam(int type, String param) {
		Mmbr member;
		switch(type) {
		case 1:  // member_id 중복 체크
	        member = memberMapper.getMemberByMemberId(param);
	        if(member!=null) return false;
		case 2:  // phone_no 중복 체크
	        member = memberMapper.getMemberByPhoneNo(param);
	        if(member!=null) return false;
		case 3:
			break;
		default:
			return false;
		}
		
		return true;
	}

	public TokenDto loginMemberByMail(Map<String, String> parameters) {

		// 필수 파라미터 추가
		String mailAddr = parameters.get("mail_addr") != null ? parameters.get("mail_addr").toString().trim() : "";  // 
		String password = parameters.get("password") != null ? parameters.get("password").toString().trim() : "";
		String encryptedPassword="";

		// 필수 파라미터 확인
        // 파라미터 체크
        ArrayList<Object> unvalidatedValues = new ArrayList<>();
        unvalidatedValues.add(mailAddr);
        unvalidatedValues.add(password);
        if(!CommonUtil.checkUnvalidatedValues(unvalidatedValues)) {
        	System.out.println("Paramater Error!");
        }
       
        // 복호화 
        try {
//			encryptedPassword = EncryptUtil.encodeSHA256(password);
			encryptedPassword =password;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       	

        Mmbr member = memberMapper.getMemberToLoginByMail(mailAddr, encryptedPassword);
        // 조건에 맞는 회원이 없으면 에러
        if(member==null) {
        	System.out.println("ERROR");
        }
        
		return setLoginSession(member);
	}

	public TokenDto loginMemberBySocial(Map<String, String> parameters) {
		// TODO Auto-generated method stub
		Mmbr member = null;
		
		return setLoginSession(member);	}

}
