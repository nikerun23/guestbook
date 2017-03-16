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
		
		//�����ϰ� �̸��� ������ �ִ� ��ƿ Ŭ����
		//���� ��� 1a5f7693-a689-4f97-bd79-aa9b530df1cf.jpg������ ���ڿ� ������
		String uniqueFileName = java.util.UUID.randomUUID().toString();
		
        int dot = originalFileName.lastIndexOf(".");// Ȯ���ڸ� ����
        String ext = originalFileName.substring(dot);  // includes "."
        result = uniqueFileName + ext;// ���� ������ ���ϸ� + .Ȯ����

		return result;
	}
}
