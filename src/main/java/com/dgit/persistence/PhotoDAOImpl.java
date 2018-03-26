package com.dgit.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.PhotoVO;

@Repository
public class PhotoDAOImpl implements PhotoDAO {
	
	private static final String namespace = "com.dgit.mapper.PhotoMapper";
	
	@Autowired
	SqlSession session;
	
	@Override
	public List<PhotoVO> listAllPhoto(String pid) {
		return session.selectList(namespace+".listAllPhoto",pid);
	}

	@Override
	public PhotoVO readPhoto(int pno) {
		return session.selectOne(namespace+".readPhoto",pno);
	}

	@Override
	public void insertPhoto(PhotoVO vo) {
		session.insert(namespace+".insertPhoto",vo);
	}

	@Override
	public void deletePhoto(int pno) {
		session.delete(namespace+".deletePhoto",pno);
	}

}
