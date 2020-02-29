package com.petting.controller.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDto {

	@JsonProperty(value="member_token")
	private String memberToken;
	
	@JsonProperty(value="member_id")
	private String memberId;
	
	@JsonProperty(value="expried_date")
	private Date expriedDate;

	
}
