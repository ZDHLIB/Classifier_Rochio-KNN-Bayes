package FileOperate;

import java.io.File;

import FileOperate.WriteFile;

/**
 * �����������
 * 1�������������ڸ�Ŀ¼��E:\������   �ļ�����
 * 2���ڸ�Ŀ¼���������������������ļ���
 * 3����Դ�ļ���  �������+����+������ ��������������Ӧ�������ļ�����
 * */
public class SaveResult {
	
	
	//���������������Ϊ����Ŀ¼������Ŀ¼�����ļ������ļ����ݡ����뷽ʽ
	//�磺parentPath="E:\\������"; childPath = "5������"; 
	//    fileName="������2010-02-12�ຣ�㲥��"; fileContent="..."; coding="GBK";
	public static void saveResult(String parentPath, String childPath, String fileName,
			               String fileContent, String type, String coding)//throws Exception
	{
		File path1 = new File(parentPath);
		File path2 = new File(parentPath+"\\"+childPath); //ע��Ҫ����д������
		
		//������Ŀ¼
		if(!path1.exists() && !path1.isFile()){
			path1.mkdir();
		}
		//������Ŀ¼
		if(!path2.exists() && !path2.isFile()){
			path2.mkdir();
		}
		//�����ļ��� ���������+����+������ �����������������ļ�����
		WriteFile writeFile = new WriteFile();
		writeFile.writeFile(fileContent, parentPath+"\\"+childPath+"\\"+fileName, type, coding);
	}
	

	/*
	public static void main(String[] args) throws Exception
	{
		String savePath = "E:\\������";
		String fileName = "������2010-10-3�ຣ�㲥��.txt";
		
		File file = new File(filePath);
		if(!file.exists() && !file.isFile()){
			file.mkdir();
		}
	}*/
}