package com.petting.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.petting.interceptor.PettingAuthType;
import com.petting.interceptor.PreAuth;
import com.petting.service.MemberService;

@RestController
@RequestMapping("/v1/members")
public class MemberController {
	
	final static String TAG = "MemberController";
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 2020.02.22 By Allen
	 * 
	 *  로그인 페이지 호출
	 * 
	 *  @return
	 */
	@PreAuth(PettingAuthType.API_KEY)
	@GetMapping(value = "/login")
    public ModelAndView getLogin() {

        ModelAndView modelAndView = new ModelAndView("member/login");
        System.out.println(TAG + " Get Login Page OK.");

        return modelAndView;
    }
	
	/**  
	 * 2020.02.28 By Allen
	 * 
	 *  이메일 (자체 회원) 로그인 요청
	 * 
	 * @param resultMap
	 * @param parameters
	 * @return
	 */
	@PreAuth(PettingAuthType.API_KEY)
    @PostMapping(value = "/login/mail")
    public ModelMap loginMemberByMail(
            @ModelAttribute ModelMap resultMap,
            @RequestBody Map<String, String> parameters
    ) {
        HashMap responseMap = Maps.newHashMap();
        
        responseMap.put("token", memberService.loginMemberByMail(parameters));
        resultMap.addAttribute("response", responseMap);
        resultMap.addAttribute("message", "Login Member By Mail OK.");
        return resultMap;
    }
	
	/**  
	 * 2020.02.28 By Allen
	 * 
	 *  소셜 로그인 요청
	 * 
	 * @param resultMap
	 * @param parameters
	 * 		type = 소셜 로그인 종류 ( 1 : KaKao, 2 : Naver )
	 * 
	 * @return
	 */
	@PreAuth(PettingAuthType.API_KEY)
    @PostMapping(value = "/login/social")
    public ModelMap loginMemberBySocial(
            @ModelAttribute ModelMap resultMap,
            @RequestBody Map<String, String> parameters
    ) {
        HashMap responseMap = Maps.newHashMap();
        responseMap.put("token", memberService.loginMemberBySocial(parameters));
        resultMap.addAttribute("response", responseMap);
        resultMap.addAttribute("message", "Login Member By Social OK.");
        return resultMap;
    }
	
	/**
	 * 2020.02.22 By Allen
	 * 
	 *  회원가입 페이지 호출
	 * 
	 *  @return
	 */
	@PreAuth(PettingAuthType.API_KEY)
	@GetMapping(value = "/join")
    public ModelAndView getJoin() {

        ModelAndView modelAndView = new ModelAndView("member/join");
        System.out.println(TAG + " Get Join Page OK.");

        return modelAndView;
    }

	/**  
	 * 2020.02.23 By Allen
	 * 
	 * 회원가입 요청
	 * 
	 * @param resultMap
	 * @param parameters
	 * @return
	 */
	@PreAuth(PettingAuthType.API_KEY)
    @PostMapping(value = "/join")
    public ModelMap joinMember(
            @ModelAttribute ModelMap resultMap,
            @RequestBody Map<String, String> parameters
    ) {
        HashMap responseMap = Maps.newHashMap();
        responseMap.put("token", memberService.joinMember(parameters));
        resultMap.addAttribute("response", responseMap);
        resultMap.addAttribute("message", "Join Member OK.");
        return resultMap;
    }
}
