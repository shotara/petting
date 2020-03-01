package com.petting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="petting.config")
public class GlobalConfig {
	
    public static final Long GLOBAL_API_TOKEN_EXPIRE_IN = (long) (60*60*1000);

	public static final Long GLOBAL_MEMBER_TOKEN_EXPIRE_IN = (long) (60*60*24*1000);
    
    
	// Security Variables
    @Value("${com.petting.config.security.aesKeys.memberId}")
    public String GLOBAL_SECURITY_AESKEYS_MEMBER_ID;

}
