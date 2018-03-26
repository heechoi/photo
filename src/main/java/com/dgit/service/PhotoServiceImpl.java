package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.PhotoVO;
import com.dgit.persistence.PhotoDAO;

@Service
public class PhotoServiceImpl implements PhotoService {
	
	@Autowired
	PhotoDAO dao;
	
	@Override
	public List<PhotoVO> photoList(String pid) {
		return dao.listAllPhoto(pid);
	}

	@Override
	public PhotoVO readPhoto(int pno) {
		return dao.readPhoto(pno);
	}

	@Override
	public void addPhoto(PhotoVO vo) {
		dao.insertPhoto(vo);
	}

	@Override
	public void removePhto(int pno) {
		dao.deletePhoto(pno);
	}

}
