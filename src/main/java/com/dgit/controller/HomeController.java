package com.dgit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.MemberVO;
import com.dgit.service.MemberService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	MemberService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(String nomember,Model model) {
		logger.info("----------- Home Get -------------");
		if(nomember!=""){
			model.addAttribute("nomember", nomember);
		}
		return "home";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(MemberVO vo,RedirectAttributes rttr){
		logger.info("----------- login  -------------");
		MemberVO member =service.checkMember(vo.getId(), vo.getPw());
		if(member ==null){
			logger.info("회원이 아님");
			rttr.addAttribute("nomember", "회원이 아닙니다. 정보를 확인 해주세요");
			return "redirect:/";
		}
	
		rttr.addAttribute("member", member.getId());
		return "redirect:photoList";
	}
	
	@RequestMapping(value="/photoList",method = RequestMethod.GET)
	public void login(){
		logger.info("----------- login  Get -------------");
	}
}
