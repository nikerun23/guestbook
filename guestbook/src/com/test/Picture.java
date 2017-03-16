package com.test;

import org.springframework.web.multipart.MultipartFile;

public class Picture {
	
	private int picId;
	private String guestBookPicFileName, comment;
	
	/* 폼 내부에 있는 <input type="file" name="file"> 엘리먼트의 name="" 속성 식별자와 일치해야 한다. */
	private MultipartFile file;
	
	public int getPicId() {
		return picId;
	}
	public void setPicId(int picId) {
		this.picId = picId;
	}
	public String getGuestBookPicFileName() {
		return guestBookPicFileName;
	}
	public void setGuestBookPicFileName(String guestBookPicFileName) {
		this.guestBookPicFileName = guestBookPicFileName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}