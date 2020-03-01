package com.petting.model.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ApiTokn {
	
	private String apiTokn;
	private String apiToknUseYn;
	private Date apiToknIsueDtm;
	private Date apiToknExprDtm;
	private Date dataCretDtm;
	private Date dataEditDtm;
	
}
