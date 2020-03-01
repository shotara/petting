package com.petting.model.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mmbr {

	private String mmbrId;
	private String mmbrPw;
	private String mmbrNm;
	private String mailAdrs;
	private String mmbrTino;
	private String mmbrStusCd;
	private String smplJoinCmpnCd;
	private String joinChnlDvsnCd;
	private Date mmbrJoinDtm;
	private Date dataCretDtm;
	private Date dataEditDtm;
	
}
