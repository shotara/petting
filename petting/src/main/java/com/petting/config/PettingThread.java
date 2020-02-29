package com.petting.config;

import java.security.PrivateKey;

import org.apache.tomcat.util.http.fileupload.RequestContext;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * this class for Request 당 하나씩 생성되는 Context
 * 필수 헤더 정보나 로깅에 필요한 정보들을 보관한다.
 *
 * @author jason.kim
 * @since 09/11/2018
 */
@JsonAutoDetect
@Data
public class PettingThread {

    @JsonIgnore
    private static ThreadLocal<PettingThread> threadLocal = ThreadLocal.withInitial(PettingThread::new);

    private String apiToken;
    
    private String memberId;
        
    @JsonIgnore
    private PrivateKey privateKey;

    public static PettingThread get() {
        return threadLocal.get();
    }

    public static void set(PettingThread pettingThread) {
    	threadLocal.set(pettingThread);
    }

    public static void clear() {
    	threadLocal.remove();
    }

}
