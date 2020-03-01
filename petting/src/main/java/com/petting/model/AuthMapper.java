package com.petting.model;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petting.model.domain.ApiTokn;

@Repository
@Mapper
@Transactional
public interface AuthMapper {

	public ApiTokn getApiToken(@Param("apiToken") String apiToken);
	public int updateApiToken(@Param("apiToken") String apiToken, @Param("expiredDate") Date expiredDate);
	

}
