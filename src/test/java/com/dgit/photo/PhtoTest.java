package com.dgit.photo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dgit.domain.PhotoVO;
import com.dgit.persistence.PhotoDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class PhtoTest {
	
	@Autowired
	PhotoDAO dao;
	
	//@Test
	public void insertPhotoTest(){
		PhotoVO vo = new PhotoVO();
		vo.setPid("test");
		vo.setPhotoName("photoname");
		dao.insertPhoto(vo);
	}
	
	//@Test
	public void readPhotoTest(){
		dao.readPhoto(1);
	}
	
	//@Test
	public void listTest(){
		dao.listAllPhoto("test");
	}
	
	@Test
	public void deleteTest(){
		dao.deletePhoto(1);
	}
	
	
	
}
