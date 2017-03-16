package com.test;

import java.util.List;
import java.util.Map;

public interface GuestBookDAO {

	// 방명록 게시물 전체 출력 (페이징 처리 전 or 페이징 처리 후)
	public List<GuestBook> guestBookList(Map<String, String> map);

	// 방명록 게시물 입력 메소드
	public int guestbookAdd(GuestBook gb);

	// 방명록 게시물 삭제 메소드
	public int guestbookRemove(GuestBook g);

	// 방명록 게시물 전체 카운트 메소드
	public int totalCount(Map<String, String> map);

	// 비밀글(Ajax 요청)에 대한 응답 처리
	public List<GuestBook> guestBookAjax(GuestBook gb);

	// 관리자용 방명록 게시물 전체출력 및 검색 메소드
	public List<GuestBook> adminBookList(Map<String, String> map);

	// 관리자용 방명록 게시물 블라인드 처리 메소드
	public int adminBlind(GuestBook g);
	
	//사진 목록 출력 메소드
	public List<Picture> pictureList();
	
	//사진 업로드 메소드
	public int pictureAdd(Picture p);

}
