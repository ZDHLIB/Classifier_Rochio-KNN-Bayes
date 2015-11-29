package FileOperate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class WriteFile {
	
	//дtxt�ı�������Ϊ���ı��ַ������ݡ��ļ���(����·��)���ı����뷽ʽ��
	public void writeFile(String fileContent, String fileName, String type, String coding){

		if(type.contains(".")) 
			fileName = fileName + type;
		else 
			fileName = fileName + "." + type;
		
		File file = new File(fileName);
	//	if(file.exists()){//������ڸ��ļ�����ɾ֮
	//		file.delete();
	//	}
	//	else{
	//		try{
	//				file.createNewFile();
	//		   }catch (IOException e){e.printStackTrace();}
	//	}
		
		if( fileContent == null || fileContent.length() == 0){
			System.out.println("the file is null!");
			return;
		}
		
		try{
			byte[] buf = ByteString.toBytes(fileContent, coding);//����ByteString���toBytes����
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buf);
			fos.close();
		}catch(Exception e){e.printStackTrace();}
	}
	/*
	/////////////////////////////////////////////////////////////////////////
	//дtxt�ı�������Ϊ���ı��ַ������ݡ��ļ������ı����뷽ʽ��  �����˼��ܹ���   //
	////////////////////////////////////////////////////////////////////////
	public static void writeBinaryFile(String fileContent, String fileName, String type, String coding){

		if(type.contains(".")) fileName = fileName + type;
		else fileName = fileName + "." + type;
		
		File file = new File(fileName);
		if(file.exists()){//������ڸ��ļ�����ɾ֮
			file.delete();
		}
		else{
			try{
					file.createNewFile();
			   }catch (IOException e){e.printStackTrace();}
		}
		
		if( fileContent == null || fileContent.length() == 0){
			System.out.println("the file is null!");
			return;
		}
		
		try{
			fileContent = MD5andKL.JIAMI(fileContent);//����
			
			byte[] buf = ByteString.toBytes(fileContent, coding);//����ByteString���toBytes����
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(buf);
			fos.close();
			//FileWriter writer = new FileWriter( fileName );//����һ�������ļ��������������
			//writer.write(new String(buf, coding));   //������д�ַ�����
			//writer.close();                           //�ر���
		}catch(Exception e){e.printStackTrace();}
	}*/
}
