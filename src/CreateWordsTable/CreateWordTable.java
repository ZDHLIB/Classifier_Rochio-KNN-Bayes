package CreateWordsTable;

import FileOperate.*;
import InterfaceMain.*;
/*
 * �����ؼ��ʴʱ�����WordsTable.txt
 */

public class CreateWordTable {

	public String CreateTable() {
		String ResultString = "";
		String[] SplitString = new String []{};
		
		ReadFromFile ReadFile = new ReadFromFile();
		WriteFile writeFile = new WriteFile();

		ResultString = ReadFile.readTxtFile("SplitResult.txt");
	//	System.out.println(ResultString);
		SplitString = ResultString.split(" ");//��ȡ�ļ����ݣ���"/*.? "�ָ� 
		ResultString = "";
		for( int i = 0; i < SplitString.length; i++ ){
			if( !SplitString[i].equals(null) ) {
				ResultString += "#"+SplitString[i];
				ResultString += "\n";
			//	System.out.println(ResultString);
			}
		}
		ResultString = ResultString.replaceAll("\\r\\n*", "");
		//�����ָ��Ĵʱ�WordsTable.txt
		writeFile.writeFile(ResultString, "WordsTable", "txt", "GBK");
		
		return ResultString;
	}
	/*
	public static void main(String[] args) {
		CreateWordTable createWordTable = new CreateWordTable();
		createWordTable.CreateTable();
	}*/
}


