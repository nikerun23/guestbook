package com.test;

import org.springframework.web.multipart.MultipartFile;

/* ���� ���ε� �������� ������ ������ �����ϴ� ������ �ð� �ȴ�. */
public class FileModel {
	
	/* �� ���ο� �ִ� <input type="file" name="file"> ������Ʈ�� name="" �Ӽ� �ĺ��ڿ� ��ġ�ؾ� �Ѵ�. */
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
