package com.dgit.service;

import com.dgit.domain.MemberVO;

public interface MemberService {
	public void addMember(MemberVO vo);
	public MemberVO checkId(String id);
	public MemberVO checkMember(String id,String pw);
}
