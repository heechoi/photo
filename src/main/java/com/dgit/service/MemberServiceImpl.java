package com.dgit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dgit.domain.MemberVO;
import com.dgit.persistence.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	MemberDAO dao;
	
	@Override
	public void addMember(MemberVO vo) {
		dao.insertMember(vo);
	}

	@Override
	public MemberVO checkId(String id) {
		return dao.checkId(id);
	}

	@Override
	public MemberVO checkMember(String id, String pw) {
		return dao.checkMember(id, pw);
	}

}
