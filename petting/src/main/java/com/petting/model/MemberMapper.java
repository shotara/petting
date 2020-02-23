package com.petting.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petting.model.domain.Member;

@Repository
@Mapper
@Transactional
public interface MemberMapper {

	Member getMemberByMemberAccessToken(@Param(value = "mmbrAccessToken") String mmbrAccessToken);

	Member getMemberByMemberId(@Param(value = "memberId") String memberId);

	Member getMemberByPhoneNo(@Param(value = "phoneNo") String phoneNo);

	int joinMember(@Param(value = "memberId") String memberId, @Param(value = "password") String password, @Param(value = "phoneNo") String phoneNo);

}
