package com.test;

public class GuestBook {
	
	//�����ͺ��̽� ���̺��� �÷����� ����� ���
	//gid, name, pw, content, writeday, blind
	private int gid, blind;
	private String name, pw, content, writeday;
	
	//IP�ּҸ� �����ϱ� ���� ��� �߰�
	private String ipaddress;
	
	//��б� �ɼ� ��� �߰�
	private int userOption;
	
	public int getUserOption() {
		return userOption;
	}
	public void setUserOption(int userOption) {
		this.userOption = userOption;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public int getBlind() {
		return blind;
	}
	public void setBlind(int blind) {
		this.blind = blind;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriteday() {
		return writeday;
	}
	public void setWriteday(String writeday) {
		this.writeday = writeday;
	}
	

}
