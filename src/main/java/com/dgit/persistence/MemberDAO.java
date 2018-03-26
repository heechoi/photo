package com.dgit.persistence;

import com.dgit.domain.MemberVO;

public interface MemberDAO {
	public void insertMember(MemberVO vo);
	public MemberVO checkId(String id);
	public MemberVO checkMember(String id,String pw);
}
