package com.test;

public class FileRenamePolicy {

	public static String rename (String originalFileName) {
		String result = "";
		/*
		long currentTime = System.currentTimeMillis();
        SimpleDateFormat simDf = new SimpleDateFormat("HHMMmmyyyyssdd");
        Random r = new Random();
        String uniqueFileName = String.format("%04d%s", r.nextInt(1000), simDf.format(new Date(currentTime)));
		 */
		
		//랜덤하게 이름을 생성해 주는 유틸 클래스
		//에를 들어 1a5f7693-a689-4f97-bd79-aa9b530df1cf.jpg형태의 문자열 생성됨
		String uniqueFileName = java.util.UUID.randomUUID().toString();
		
        int dot = originalFileName.lastIndexOf(".");// 확장자를 저장
        String ext = originalFileName.substring(dot);  // includes "."
        result = uniqueFileName + ext;// 새로 생성한 파일명 + .확장자

		return result;
	}
}
