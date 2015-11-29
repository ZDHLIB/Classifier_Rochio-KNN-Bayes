package FileOperate;

import java.io.File;
import java.util.LinkedList;

public class GetFileName {
	
	/**
	 * ���ܣ�     ��ȡһ���ļ����µ������ļ�������(�����������ļ��е�����)
	 * ������     filePathName ������һ���ļ��е�·��,�磺D:\html
	 * ����ֵ��(1) null      ��ʾ���κ��ļ������
	 * 		   (2) String[] �ļ�������(���ٰ���һ���ļ���)
	 */
	public static String[] getAllFileName(String filePathName)
	{
		File file = new File(filePathName);
		if( !file.isDirectory() ){         //�������filePathName����·��
			System.out.println("error from myapi.GetFileName.getFileName:"+filePathName+"����һ��·��!");
			return null;
		}
		
		File[] fileList = file.listFiles();
		if(fileList==null || fileList.length==0){
			System.out.println("error from myapi.GetFileName.getFileName:�޷���ȡĿ¼"+filePathName+"�µ��ļ���,���Ŀ¼�����κ��ļ�!");
			return null;
		}
		
		String[] fileNameList = new String[fileList.length];
		
		for(int i=0; i<fileList.length; i++){
			fileNameList[i] = fileList[i].getName();
		}
		return fileNameList;
	}
	
	/**
	 * ���ܣ�     ��Ŀ¼����Ŀ¼�µ������ļ�������(����·����)
	 * ������     filePathName ������һ���ļ��е�·��,�磺D:\html
	 * ����ֵ��(1) null      ��ʾ���κ��ļ������
	 * 		   (2) String[] �ļ�������(���ٰ���һ���ļ���)
	 */
	
	public static String[] getAllFileName_2(String filePathName)
	{
	   String fileNamesStr = getFileNameStr(new File(filePathName));
	   return fileNamesStr.split("\r\n");
    }  

	private static String getFileNameStr(File file){
		//�ж��ļ��Ƿ����  
		if (file.exists()) {  
		      if(!file.isFile()){    
	             File[] fl = file.listFiles();
	             String tmp = "";
	             for (File f : fl)  
	            	 tmp += getFileNameStr(f) + "\r\n";
	              return tmp;  
	          }else{ 
	             return file.getAbsolutePath();  
	           }  
	    }else{  
	          System.out.println("�ļ������ļ��в����ڣ�����·���Ƿ���ȷ��");  
		      return null;  
	    }  
	}
	
	
	/**
	 * ���ܣ�      ��ȡһ���ļ�����ָ����ʽ���ļ����б�
	 * ������      filePathName  һ���ļ��е�·��
	 * 	       fileType       �ļ���ʽ����'html' ��  'htm' �� 'txt'
	 *  
	 * ����ֵ��(1)null     ��ʾ���յ��Ĳ����д���
	 * 	      (2)String[]  ����ָ����ʽ���ļ����б�(���ٰ���һ���ļ�)
	 */
	public static String[] getFileName(String filePathName, String []fileType)
	{
		File file = new File(filePathName);
		if( !file.isDirectory() ){         //�������filePathName����·��
			System.out.println("error from myapi.GetFileName.getFileName:"+filePathName+"����һ��·��!");
			return null;
		}
		if(fileType==null || fileType.length==0){
			System.out.println("error from myapi.GetFileName.getFileName:"+filePathName+"�ļ�����ָ������!");
			return null;
		}
		
		//��������Ϸ������������Ĳ���
		File[] fileNameList = file.listFiles();           //��ȡ��Ŀ¼�������ļ����ļ����б�
		if(fileNameList==null || fileNameList.length==0){
			System.out.println("error from myapi.GetFileName.getFileName:�޷���ȡĿ¼"+filePathName+"�µ��ļ���,���Ŀ¼�����κ��ļ�!");
			return null;
		}
		//��ȡָ����ʽ���ļ���
		LinkedList<String> fileNames = new LinkedList<String>();  //����ָ����ʽ���ļ���
		String type="";
		for(int i=0; i<fileNameList.length; i++){
			type = fileNameList[i].getName();
			if( !type.contains(".") ) continue;
			type = type.substring(type.lastIndexOf('.')+1, type.length());  //��ȡ�ļ�����׺(���ļ���ʽ)
			for(int j=0; j<fileType.length; j++){
				if(fileType[j].equalsIgnoreCase(type)){                     //�����ִ�Сд
					fileNames.add(fileNameList[i].getName());
				}
			}
		}
		//�������
		if(fileNames.size()==0){
			System.out.println("error from myapi.GetFileName.getFileName:Ŀ¼"+filePathName+"����ָ����ʽ���ļ�!");
			return null;
		}else {
			String []tmp = new String[fileNames.size()];
			for(int i=0; i<tmp.length; i++){
				tmp[i] = fileNames.get(i);
			}
			return tmp;
		}
	}
	
	//======================================================================
	public static void main(String[] args)
	{
		String path =  ".\\configureFile_WebSite";
		String[] names =  getAllFileName_2(path);
		if(names!=null){
			for(int i=0; i<names.length; i++){
				System.out.println(names[i]);
			}
		}
	}
}