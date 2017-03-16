package com.test;

import java.util.List;
import java.util.Map;

public interface GuestBookDAO {

	// ���� �Խù� ��ü ��� (����¡ ó�� �� or ����¡ ó�� ��)
	public List<GuestBook> guestBookList(Map<String, String> map);

	// ���� �Խù� �Է� �޼ҵ�
	public int guestbookAdd(GuestBook gb);

	// ���� �Խù� ���� �޼ҵ�
	public int guestbookRemove(GuestBook g);

	// ���� �Խù� ��ü ī��Ʈ �޼ҵ�
	public int totalCount(Map<String, String> map);

	// ��б�(Ajax ��û)�� ���� ���� ó��
	public List<GuestBook> guestBookAjax(GuestBook gb);

	// �����ڿ� ���� �Խù� ��ü��� �� �˻� �޼ҵ�
	public List<GuestBook> adminBookList(Map<String, String> map);

	// �����ڿ� ���� �Խù� ����ε� ó�� �޼ҵ�
	public int adminBlind(GuestBook g);
	
	//���� ��� ��� �޼ҵ�
	public List<Picture> pictureList();
	
	//���� ���ε� �޼ҵ�
	public int pictureAdd(Picture p);

}
