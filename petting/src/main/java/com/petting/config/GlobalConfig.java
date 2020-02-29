package com.petting.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="petting.config")
public class GlobalConfig {
	
    // Security Variables
    @Value("${com.petting.config.security.aesKeys.memberId}")
    public String GLOBAL_SECURITY_AESKEYS_MEMBER_ID;

}
