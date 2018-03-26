package com.dgit.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.MemberVO;
import com.dgit.domain.PhotoVO;
import com.dgit.service.MemberService;
import com.dgit.service.PhotoService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	MemberService service;
	
	@Autowired
	PhotoService photoService;
	
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
	public void login(Model model,HttpServletRequest requset){
		logger.info("----------- login  Get -------------");
		String id = (String)requset.getSession().getAttribute("login");
		
		List<PhotoVO> vo = photoService.photoList(id);
	
		model.addAttribute("photoList",vo);
	}
	
	@RequestMapping(value="/idCheck",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<MemberVO> idCheckTest(String id){
		
		ResponseEntity<MemberVO> entity = null;
		
		try{
			MemberVO vo =service.checkId(id);
			entity = new ResponseEntity<MemberVO>(vo,HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<MemberVO>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/join",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addMember(@RequestBody MemberVO vo){
		ResponseEntity<String> entity = null;
		try{
			service.addMember(vo);
			entity = new ResponseEntity<String>("success",HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
}
