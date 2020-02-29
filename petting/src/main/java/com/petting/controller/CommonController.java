package com.petting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.petting.interceptor.PettingAuthType;
import com.petting.interceptor.PreAuth;

@RestController
@RequestMapping("/")
public class CommonController {

	@PreAuth(PettingAuthType.API_KEY)
	@GetMapping(value = "/home")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
	
}
