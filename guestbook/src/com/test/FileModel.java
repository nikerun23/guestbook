package com.test;

import org.springframework.web.multipart.MultipartFile;

/* 파일 업로드 과정에서 파일의 정보를 저장하는 역할을 맡게 된다. */
public class FileModel {
	
	/* 폼 내부에 있는 <input type="file" name="file"> 엘리먼트의 name="" 속성 식별자와 일치해야 한다. */
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
