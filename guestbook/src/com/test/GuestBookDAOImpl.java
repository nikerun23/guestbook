package com.test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GuestBookDAOImpl implements GuestBookDAO {
	
	/* MyBatis ���� �߰� */
	/* SqlSession ��ü�� ���� �ڵ� �������� */
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<GuestBook> guestBookList(Map<String, String> map) {
		GuestBookDAO dao = (GuestBookDAO) sqlSession.getMapper(GuestBookDAO.class);
		List<GuestBook> list = dao.guestBookList(map);
		return list;
	}

	@Override
	public List<GuestBook> guestBookAjax(GuestBook gb) {
		GuestBookDAO dao = (GuestBookDAO) sqlSession.getMapper(GuestBookDAO.class);
		List<GuestBook> result = dao.guestBookAjax(gb);
		return result;
	}

	@Override
	public int guestbookAdd(GuestBook gb) {
		GuestBookDAO dao = (GuestBookDAO) sqlSession.getMapper(GuestBookDAO.class);
		int result = dao.guestbookAdd(gb);
		return result;
	}

	@Override
	public int guestbookRemove(GuestBook gb) {
		GuestBookDAO dao = (GuestBookDAO) sqlSession.getMapper(GuestBookDAO.class);
		int result = dao.guestbookRemove(gb);
		return result;
	}

	@Override
	public int totalCount(Map<String, String> map) {
		GuestBookDAO dao = (GuestBookDAO) sqlSession.getMapper(GuestBookDAO.class);
		int result = dao.totalCount(map); //Integer -> int
		return result;
	}

	@Override
	public List<GuestBook> adminBookList(Map<String, String> map) {
		GuestBookDAO dao = (GuestBookDAO) sqlSession.getMapper(GuestBookDAO.class);

		return null;
	}

	@Override
	public int adminBlind(GuestBook g) {
		GuestBookDAO dao = (GuestBookDAO) sqlSession.getMapper(GuestBookDAO.class);
		
		
		return 0;
	}
}