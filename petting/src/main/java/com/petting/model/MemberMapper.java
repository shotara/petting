package com.petting.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petting.model.domain.Mmbr;

@Repository
@Mapper
@Transactional
public interface MemberMapper {

	Mmbr getMemberByMemberAccessToken(@Param(value = "mmbrAccessToken") String mmbrAccessToken);

	Mmbr getMemberByMemberId(@Param(value = "memberId") String memberId);

	Mmbr getMemberByPhoneNo(@Param(value = "phoneNo") String phoneNo);

	int joinMember(@Param(value = "memberId") String memberId, @Param(value = "password") String password, @Param(value = "phoneNo") String phoneNo);

	Mmbr getMemberToLoginByMail(@Param(value = "mailAddr") String mailAddr, @Param(value = "memberPassword") String memberPassword);

}
