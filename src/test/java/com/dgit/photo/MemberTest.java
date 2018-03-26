package com.dgit.photo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.MemberVO;
import com.dgit.persistence.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class MemberTest {
	
	@Autowired
	MemberDAO dao;
	
	//@Test
	public void insertMemberTest(){
		MemberVO vo = new MemberVO();
		vo.setId("test");
		vo.setEmail("test@test.com");
		vo.setName("name");
		vo.setPhone("phone");
		vo.setPw("password");
		dao.insertMember(vo);
	}
	
	
	//@Test
	public void idTest(){
		dao.checkId("test");
	}
	
	
	@Test
	public void idpwTest(){
		dao.checkMember("test", "password");
	}
	
	
}
