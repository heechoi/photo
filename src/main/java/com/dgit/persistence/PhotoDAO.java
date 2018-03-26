package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.Criteria;
import com.dgit.domain.PhotoVO;

public interface PhotoDAO {
	public List<PhotoVO> listAllPhoto(String pid,Criteria cri);
	public PhotoVO readPhoto(int pno);
	public void insertPhoto(PhotoVO vo);
	public void deletePhoto(int pno);
	public int countPagin(String pid);
}
