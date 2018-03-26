package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dgit.domain.Criteria;
import com.dgit.domain.MemberVO;
import com.dgit.domain.PageMaker;
import com.dgit.domain.PhotoVO;
import com.dgit.service.MemberService;
import com.dgit.service.PhotoService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

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
	
	@Resource(name="uploadPath")
	private String outUploadPath;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(String nomember,Model model) {
		logger.info("----------- Home Get -------------");
		if(nomember!=""){
			model.addAttribute("nomember", nomember);
		}
		return "home";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(MemberVO vo,RedirectAttributes rttr,Criteria cri){
		logger.info("----------- login  -------------");
		MemberVO member =service.checkMember(vo.getId(), vo.getPw());
		if(member ==null){
			logger.info("회원이 아님");
			rttr.addAttribute("nomember", "회원이 아닙니다. 정보를 확인 해주세요");
			return "redirect:/";
		}
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("member", member.getId());
		return "redirect:photoList";
	}
	
	@RequestMapping(value="/photoList",method = RequestMethod.GET)
	public void login(Model model,HttpServletRequest requset, @ModelAttribute("cri") Criteria cri){
		logger.info("----------- login  Get -------------");
		String id = (String)requset.getSession().getAttribute("login");
		
		
		PageMaker pageMaker = new  PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(photoService.totalCount(id));
	
		List<PhotoVO> vo = photoService.photoList(id,cri);
		
		model.addAttribute("photoList",vo);
		model.addAttribute("pageMaker", pageMaker);
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
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String uploadPhoto(List<MultipartFile> files,HttpServletRequest request,RedirectAttributes rttr,Criteria cri) throws IOException, Exception{
		logger.info("upload POST");
		PhotoVO vo = new PhotoVO();
		String id = (String)request.getSession().getAttribute("login");
		
		if(files !=null && files.get(0).getSize()>0){
			for(int i=0;i<files.size();i++){
				String savedName =  UploadFileUtils.uploadFile(outUploadPath, files.get(i).getOriginalFilename(), files.get(i).getBytes());
				vo.setPhotoName(savedName);
				vo.setPid(id);
				photoService.addPhoto(vo);
			}

		}
		rttr.addAttribute("page", cri.getPage());
		return "redirect:/photoList";
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.GET)
	public String redirect(RedirectAttributes rttr,Criteria cri){
		rttr.addAttribute("page", cri.getPage());
		return "redirect:/";
	}
	
	@RequestMapping(value="/displayFile",method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		ResponseEntity<byte[]> entity = null;

		logger.info("[filename]:"+filename);
		InputStream in =null;
		
		try {
			//jpg,png인지를 구분
			String formatName = filename.substring(filename.lastIndexOf(".")+1);
			MediaType type = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(type);
			
			in = new FileInputStream(outUploadPath+filename);
			
			
			entity = new ResponseEntity<>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/remove")
	public String removePhoto(int pno){
		logger.info("remove======================");
		PhotoVO vo = photoService.readPhoto(pno);
		
		String filename = vo.getPhotoName();
		System.gc();
		
		String front = filename.substring(0,12);
		String end = filename.substring(14);
		
		String orignalFileName = front+end;
	
		
		File showfile = new File(outUploadPath+filename);
		showfile.delete();
		
		System.gc();
		
		File orignalFile = new File(outUploadPath+orignalFileName);
	
		orignalFile.delete();
		
		photoService.removePhto(pno);
		
		return "redirect:/photoList";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logoutGet(HttpServletRequest request){
		request.getSession().invalidate();
		return "redirect:/";
	}
}
