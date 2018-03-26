package com.dgit.photo;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//제일 먼저 테스트 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class DataSourceTest {
	
	@Autowired
	private DataSource ds;
	
	@Test
	public void testConnection(){
		Connection conn =null;
		
		try {
			conn = ds.getConnection();
			System.out.println(conn);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
