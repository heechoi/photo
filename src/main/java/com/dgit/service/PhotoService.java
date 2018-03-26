package com.dgit.service;

import java.util.List;

import com.dgit.domain.PhotoVO;

public interface PhotoService {
	public List<PhotoVO> photoList(String pid);
	public PhotoVO readPhoto(int pno);
	public void addPhoto(PhotoVO vo);
	public void removePhto(int pno);
	
}
