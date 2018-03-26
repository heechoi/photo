package com.dgit.persistence;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	private static final String namespace = "com.dgit.mapper.MemberMapper";
	
	@Autowired
	SqlSession session;
	
	@Override
	public void insertMember(MemberVO vo) {
		session.insert(namespace+".insertMember",vo);
	}

	@Override
	public MemberVO checkId(String id) {
		return session.selectOne(namespace+".checkId",id);
	}

	@Override
	public MemberVO checkMember(String id, String pw) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("pw", pw);
		return session.selectOne(namespace+".checkMember",map);
	}

}
