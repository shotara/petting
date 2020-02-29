package com.petting.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petting.config.GlobalConfig;
import com.petting.controller.dto.MemberDto;
import com.petting.controller.dto.TokenDto;
import com.petting.model.MemberMapper;
import com.petting.model.domain.Member;
import com.petting.util.CommonUtil;
import com.petting.util.EncryptUtil;

import ch.qos.logback.core.subst.Token;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private GlobalConfig globalConfig;
	
	public Member getMemberByMemberAccessToken(String decryptedMemberAccessToken) {
		return memberMapper.getMemberByMemberAccessToken(decryptedMemberAccessToken);
	}

	public TokenDto setLoginSession(Member member) {
		TokenDto tokenDto = new TokenDto(); 
		
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
		Member member;
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
		// TODO Auto-generated method stub
		Member member = null;
		
		return setLoginSession(member);
	}

	public TokenDto loginMemberBySocial(Map<String, String> parameters) {
		// TODO Auto-generated method stub
		Member member = null;
		
		return setLoginSession(member);	}

}
