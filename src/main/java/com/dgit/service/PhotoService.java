package com.dgit.service;

import java.util.List;

import com.dgit.domain.Criteria;
import com.dgit.domain.PhotoVO;

public interface PhotoService {
	public List<PhotoVO> photoList(String pid,Criteria cri);
	public PhotoVO readPhoto(int pno);
	public void addPhoto(PhotoVO vo);
	public void removePhto(int pno);
	public int totalCount(String pid);
}
