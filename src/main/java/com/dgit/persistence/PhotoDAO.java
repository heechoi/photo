package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.PhotoVO;

public interface PhotoDAO {
	public List<PhotoVO> listAllPhoto(String pid);
	public PhotoVO readPhoto(int pno);
	public void insertPhoto(PhotoVO vo);
	public void deletePhoto(int pno);
}
